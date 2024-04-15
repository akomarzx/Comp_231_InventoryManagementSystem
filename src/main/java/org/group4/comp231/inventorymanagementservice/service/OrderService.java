package org.group4.comp231.inventorymanagementservice.service;

import jakarta.transaction.Transactional;
import org.group4.comp231.inventorymanagementservice.domain.OrderStatusChange;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.*;
import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeBook;
import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeValue;
import org.group4.comp231.inventorymanagementservice.dto.order.OrderDto;
import org.group4.comp231.inventorymanagementservice.dto.order.OrderItemDTO;
import org.group4.comp231.inventorymanagementservice.dto.order.ProcessOrderDTO;
import org.group4.comp231.inventorymanagementservice.mapper.order.OrderMapper;
import org.group4.comp231.inventorymanagementservice.repository.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// TODO: Packing List, Invoice,

@Service
public class OrderService extends BaseService {

    private final TenantIdentifierResolver tenantIdentifierResolver;
    private final InventoryRepository inventoryRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final StaticCodeService staticCodeService;
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
    private final OrderStatusChangeRepository orderStatusChangeRepository;

    public OrderService(TenantIdentifierResolver tenantIdentifierResolver, InventoryRepository inventoryRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository, AccountRepository accountRepository, StaticCodeService staticCodeService, OrderStatusChangeRepository orderStatusChangeRepository) {
        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.inventoryRepository = inventoryRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
        this.staticCodeService = staticCodeService;
        this.orderStatusChangeRepository = orderStatusChangeRepository;
    }

    /**
     * Get All orders by type
     * @param type
     * @return List of Orders
     */
    @Transactional
    public List<Order> getAllOrders(String type) {
        Long orderTypeId = getOrderTypeCodeValueId(type);
        List<Order> orders = orderRepository.findByOrderTypeAndTenant(orderTypeId, this.tenantIdentifierResolver.resolveCurrentTenantIdentifier());
        for (Order order : orders) {
            order.setStatusCodeId(order.getOrderStatus().getCode());
            for (OrderStatusChange statusChange : order.getOrderStatusChanges()) {
                statusChange.setStatusCodeId(statusChange.getOrderStatus().getCode());
            }
        }

        return orders;
    }

    /**
     * Create New Order Purchase/SALES
     * @param dto
     * @param createdBy
     * @throws Exception
     */
    @Transactional
    public void createOrder(OrderDto dto, String createdBy) throws Exception {

        validateCreateOrderRequest(dto);

        Order newOrder = this.orderMapper.toEntity(dto);

        OrderStatus initialStatus = dto.orderType().equals(this.staticCodeService.SALES_ORDER_CODE_VALUE_ID)
                ? OrderStatus.SALES_ORDER_PENDING
                : OrderStatus.PURCHASE_ORDER_PENDING;

        newOrder.setCreatedAt(Instant.now());
        newOrder.setCreatedBy(createdBy);
        newOrder.setTenant(this.tenantIdentifierResolver.resolveCurrentTenantIdentifier());
        newOrder.setOrderStatus(initialStatus);
        newOrder = this.orderRepository.save(newOrder);

        // Keep Track of Status Changes
        OrderStatusChange newStatusChange = new OrderStatusChange();
        newStatusChange.setOrderStatus(initialStatus);
        newStatusChange.setOrder(newOrder.getId());
        newStatusChange.setCreatedAt(Instant.now());
        newStatusChange.setCreatedBy(createdBy);
        this.orderStatusChangeRepository.save(newStatusChange);

        for (OrderItemDTO orderItemDTO : dto.orderItems()) {
            Optional<Inventory> inventory = this.inventoryRepository.findById(orderItemDTO.id());
            if (inventory.isEmpty()) {
                throw new Exception("Inventory item not found - ID: " + orderItemDTO.id());
            } else {
                if (dto.orderType().equals(this.staticCodeService.SALES_ORDER_CODE_VALUE_ID)) {
                    if (inventory.get().getQuantity() < orderItemDTO.quantity()) {
                        throw new Exception("Not enough item for this inventory - ID: " + orderItemDTO.id());
                    }
                    // Sales Order Reduce Stock Available After Creating
                    inventory.get().setQuantity(inventory.get().getQuantity() - orderItemDTO.quantity());
                }
            }

            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setOrder(newOrder.getId());
            newOrderItem.setCreatedBy(createdBy);
            newOrderItem.setCreatedAt(Instant.now());
            newOrderItem.setInventory(orderItemDTO.id());
            newOrderItem.setQuantity(orderItemDTO.quantity());
            newOrderItem.setTenant(tenantIdentifierResolver.resolveCurrentTenantIdentifier());
            newOrderItem.setQuantityProcessed(0);
            this.orderItemRepository.save(newOrderItem);
        }

    }

    /**
     * Update Existing Sales Order
     *
     * @param orderId
     * @param salesOrderDTO
     * @param updatedBy
     * @throws Exception
     */
    @Transactional
    public void updateSalesOrder(Long orderId, ProcessOrderDTO salesOrderDTO, String updatedBy) throws Exception {

        OrderStatus nextUpdateStatus = resolveOrderStatus(salesOrderDTO.processCodeValueId());
        Optional<Order> currentOrder = this.orderRepository.findById(orderId);
        List<OrderStatusChange> orderStatusChanges = this.getOrderStatusChanges(orderId);

        if (currentOrder.isEmpty()) {
            throw new Exception("Order not found - ID: " + orderId);
        }

        if (currentOrder.get().getOrderStatus().equals(OrderStatus.SALES_ORDER_CLOSED)) {
            throw new Exception("Order Closed/Completed - ID: " + orderId);
        }

        switch(nextUpdateStatus) {
            // Invoicing can be created even after packing or creating packing list.
            case SALES_ORDER_INVOICED -> {
                if (!checkIfOrderStatusChangeExist(OrderStatus.SALES_ORDER_INVOICED, orderStatusChanges)) {
                    createOrderStatusChange(updatedBy, OrderStatus.SALES_ORDER_INVOICED, orderId);
                }
            }
            // Orders can not be marked as paid if invoice was not generated yet
            case SALES_ORDER_PAID -> {
                if (checkIfOrderStatusChangeExist(OrderStatus.SALES_ORDER_INVOICED, orderStatusChanges)) {
                    createOrderStatusChange(updatedBy, OrderStatus.SALES_ORDER_PAID, orderId);
                } else {
                    throw new Exception("Invoice was not generated yet.");
                }
            }
            // If Stage Order Packed was passed need to have a valid listItem to update
            case SALES_ORDER_PACKED -> {
                if (salesOrderDTO.orderItems() == null || salesOrderDTO.orderItems().isEmpty()) {
                    throw new Exception("Order Items cannot be null or empty when creating packing list");
                }

                for (OrderItemDTO orderItemDTO : salesOrderDTO.orderItems()) {
                    OrderItem orderItem = currentOrder.get().getOrderItems()
                            .stream()
                            .filter(oi -> oi.getId().equals(orderItemDTO.id()))
                            .findFirst().orElseThrow();

                    if (orderItem.getQuantityProcessed() + orderItemDTO.quantity() > orderItem.getQuantity()) {
                        throw new Exception("Quantity exceeds available Quantity to be processed");
                    }

                    orderItem.setQuantityProcessed(orderItem.getQuantityProcessed() + orderItemDTO.quantity());
                    orderItem.setUpdatedAt(Instant.now());
                    orderItem.setUpdatedBy(updatedBy);
                    this.orderItemRepository.save(orderItem);
                }

                if (!checkIfOrderStatusChangeExist(OrderStatus.SALES_ORDER_PACKED, orderStatusChanges)) {
                    createOrderStatusChange(updatedBy, OrderStatus.SALES_ORDER_PACKED, orderId);
                } else {
                }
            }
            // For brevity, we can only mark something as shipped when everything was packed
            case SALES_ORDER_SHIPPED -> {
                List<OrderItem> orderItems = new ArrayList<>(currentOrder.get().getOrderItems());

                for (OrderItem orderItem : orderItems) {
                    if (orderItem.getQuantityProcessed() < orderItem.getQuantity()) {
                        throw new Exception("Not all items are packed yet.");
                    }
                }

                createOrderStatusChange(updatedBy, OrderStatus.SALES_ORDER_SHIPPED, orderId);
                createOrderStatusChange(updatedBy, OrderStatus.SALES_ORDER_CLOSED, orderId);
                currentOrder.get().setOrderStatus(OrderStatus.SALES_ORDER_CLOSED);
                currentOrder.get().setUpdatedAt(Instant.now());
                currentOrder.get().setUpdatedBy(updatedBy);
                this.orderRepository.save(currentOrder.get());
            }
            default -> {
                throw new Exception("Invalid Process Id");
            }
        }
    }
    @Transactional
    public void updatePurchaseOrder(Long orderId, ProcessOrderDTO purchaseOrderDto, String updatedBy) throws Exception {

            OrderStatus nextUpdateStatus = resolveOrderStatus(purchaseOrderDto.processCodeValueId());
            Optional<Order> currentOrder = this.orderRepository.findById(orderId);
            List<OrderStatusChange> orderStatusChanges = this.getOrderStatusChanges(orderId);

            if (currentOrder.isEmpty()) {
                throw new Exception("Order not found - ID: " + orderId);
            }

            if (currentOrder.get().getOrderStatus().equals(OrderStatus.PURCHASE_ORDER_CLOSED)) {
                throw new Exception("Order Closed/Completed - ID: " + orderId);
            }

            switch(nextUpdateStatus) {

                case PURCHASE_ORDER_PO_SENT -> {
                    if (!checkIfThereAreLaterStagesOrStageAlreadyExist(OrderStatus.PURCHASE_ORDER_PO_SENT, orderStatusChanges)) {
                        createOrderStatusChange(updatedBy, OrderStatus.PURCHASE_ORDER_PO_SENT, orderId);
                        currentOrder.get().setOrderStatus(OrderStatus.PURCHASE_ORDER_PO_SENT);
                        this.orderRepository.save(currentOrder.get());
                    }
                }
                // Create Partially received if not all items are received
                case PURCHASE_ORDER_RECEIVED -> {

                    if (purchaseOrderDto.orderItems() == null || purchaseOrderDto.orderItems().isEmpty()) {
                        throw new Exception("Order Items cannot be null or empty when Receiving");
                    }

                    for (OrderItemDTO orderItemDTO : purchaseOrderDto.orderItems()) {
                        OrderItem orderItem = currentOrder.get().getOrderItems()
                                .stream()
                                .filter(oi -> oi.getId().equals(orderItemDTO.id()))
                                .findFirst().orElseThrow(() -> new Exception("Order Item not found"));

                        if (orderItem.getQuantityProcessed() + orderItemDTO.quantity() > orderItem.getQuantity()) {
                            throw new Exception("Quantity exceeds available quantity to be processed");
                        }

                        Optional<Inventory> inventory = this.inventoryRepository.findById(orderItem.getInventory());

                        if (inventory.isEmpty()) {
                            throw new Exception("Inventory Item not found" );
                        }

                        inventory.get().setUpdatedBy(updatedBy);
                        inventory.get().setUpdatedAt(Instant.now());
                        inventory.get().setQuantity(inventory.get().getQuantity() + orderItemDTO.quantity());
                        this.inventoryRepository.save(inventory.get());

                        orderItem.setQuantityProcessed(orderItem.getQuantityProcessed() + orderItemDTO.quantity());
                        orderItem.setUpdatedAt(Instant.now());
                        orderItem.setUpdatedBy(updatedBy);
                        this.orderItemRepository.save(orderItem);
                    }

                    List<OrderItem> orderItems = new ArrayList<>(currentOrder.get().getOrderItems());
                    boolean isPartial = false;

                    for (OrderItem orderItem : orderItems) {
                        if (orderItem.getQuantityProcessed() < orderItem.getQuantity()) {
                            if (!checkIfOrderStatusChangeExist(OrderStatus.PURCHASE_ORDER_PARTIALLY_RECEVIED, orderStatusChanges)) {
                                createOrderStatusChange(updatedBy, OrderStatus.PURCHASE_ORDER_PARTIALLY_RECEVIED, orderId);
                            }
                            isPartial = true;
                            break;
                        }
                    }

                    if (isPartial) {
                        currentOrder.get().setOrderStatus(OrderStatus.PURCHASE_ORDER_PARTIALLY_RECEVIED);
                    } else {
                        createOrderStatusChange(updatedBy, OrderStatus.PURCHASE_ORDER_RECEIVED, orderId);
                        createOrderStatusChange(updatedBy, OrderStatus.PURCHASE_ORDER_CLOSED, orderId);
                        currentOrder.get().setOrderStatus(OrderStatus.PURCHASE_ORDER_CLOSED);
                    }

                    currentOrder.get().setUpdatedAt(Instant.now());
                    currentOrder.get().setUpdatedBy(updatedBy);
                    this.orderRepository.save(currentOrder.get());

                }
                default -> {
                    throw new Exception("Invalid Process Id");
                }
            }
    }

    private void validateCreateOrderRequest(OrderDto dto) throws Exception {

        if (dto.orderType().equals(this.staticCodeService.SALES_ORDER_CODE_VALUE_ID) && dto.warehouse() != null) {
            throw new Exception("Sales Order cannot be shipped to your own warehouse");
        }

        Optional<Account> selectedAccount = this.accountRepository.findByTenantAndId(this.tenantIdentifierResolver.resolveCurrentTenantIdentifier(), dto.account());
        if (selectedAccount.isEmpty()) {
            throw new Exception("Selected Account does not exist");
        } else {
            if (dto.orderType().equals(this.staticCodeService.SALES_ORDER_CODE_VALUE_ID) &&
                    Objects.equals(selectedAccount.get().getAccountType(), this.staticCodeService.VENDOR_ACCOUNT_CODE_VALUE_ID)) {
                throw new Exception("Account selected is for purchase order");
            }
            if (dto.orderType().equals(this.staticCodeService.PURCHASE_ORDER_CODE_VALUE_ID) &&
                    Objects.equals(selectedAccount.get().getAccountType(), this.staticCodeService.SELLER_ACCOUNT_CODE_VALUE_ID)) {
                throw new Exception("Account selected is for sales order");
            }
        }
    }

    /**
     * Get Next Stage for the Order
     * @param orderType Specify type of order, Purchase Or Sales. Pass in the ID from the static code for the correct order type
     * @param currentStage - Stage of the order that is being process
     * @return OrderStatus Next Stage for the current Order
     * @throws Exception - Throw Exception if invalid codebook id, invalid current stage
     * @deprecated
     */
    private OrderStatus getNextStage(Long orderType, Long currentStage) throws Exception {

        Long codeBookId = orderType.equals(this.staticCodeService.SALES_ORDER_CODE_VALUE_ID)
                ? this.staticCodeService.CODEBOOK_SALES_ORDER_STATUS_ID
                : this.staticCodeService.CODEBOOK_PURCHASE_ORDER_STATUS_ID;

        Optional<CodeBook> codeBook = this.staticCodeService.getCodeValueListUsingCodeBookID(codeBookId);

        if (codeBook.isEmpty()) {
            throw  new Exception("Unknown error had occurred");
        }

        List<CodeValue> valList = codeBook.get().getCodeValues().stream().toList();

        int index = IntStream.range(0, valList.size())
                .filter(streamIndex -> currentStage.equals(valList.get(streamIndex).getId()))
                .findFirst()
                .orElse(-1);

        if (index == -1) {
            throw new Exception("Unknown error had occurred");
        } else {

            if (index >= valList.size() - 1) {
                throw new Exception("Order cannot be move to the next stage as this order was complete.");
            } else {
                return resolveOrderStatus(valList.get(index + 1).getId());
            }
        }
    }

    /**
     * Resolve Order Status Using ID
     * @param orderTypeId
     * @return OrderStatus that corresponds with the Code Value ID
     * @throws Exception Throws an exception when Code Value ID supplied was not found
     */
    private OrderStatus resolveOrderStatus(Long orderTypeId) throws Exception {
        return Stream.of(OrderStatus.values())
                .filter(c -> c.getCode().equals(orderTypeId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Get all past process for the order will be used to decide the correct next step for every order
     * @param orderId ID for the order the is being processed
     * @return List of Status Change for this order
     */
    List<OrderStatusChange> getOrderStatusChanges(Long orderId) {
        return orderStatusChangeRepository.findByOrder(orderId);
    }

    /**
     * Check if order status change history exist for the current order.
     * @param status Order Status To check
     * @return Boolean indication order had been processed to this stage.
     */
    public Boolean checkIfOrderStatusChangeExist(OrderStatus status, List<OrderStatusChange> orderStatusChanges) {
        return orderStatusChanges.stream()
                .anyMatch(orderStatusChange -> orderStatusChange.getOrderStatus().equals(status));
    }

    /**
     *
     * @param status Status To check
     * @param orderStatusChanges History of status changes for the order
     * @return Boolean to indicate if there are stage higher or same than this already exist
     */
    public Boolean checkIfThereAreLaterStagesOrStageAlreadyExist(OrderStatus status, List<OrderStatusChange> orderStatusChanges) {
        return orderStatusChanges.stream()
                .anyMatch(orderStatusChange -> orderStatusChange.getOrderStatus().getCode() >= status.getCode());
    }

    private void createOrderStatusChange(String createdBy, OrderStatus orderStatus, Long orderId) {
        OrderStatusChange orderStatusChange = new OrderStatusChange();
        orderStatusChange.setCreatedBy(createdBy);
        orderStatusChange.setOrderStatus(orderStatus);
        orderStatusChange.setCreatedAt(Instant.now());
        orderStatusChange.setOrder(orderId);
        this.orderStatusChangeRepository.save(orderStatusChange);
    }
    private Long getOrderTypeCodeValueId(String type) {

        final String ORDER_TYPE_SALES = "sales";
        final String ORDER_TYPE_PURCHASE = "purchase";
        Long codeValueId = null;

        if(type == null) {
            return  codeValueId;
        }

        if(type.equals(ORDER_TYPE_SALES)) {
            codeValueId = this.staticCodeService.SALES_ORDER_CODE_VALUE_ID;
        }
        else if (type.equals(ORDER_TYPE_PURCHASE)) {
            codeValueId = this.staticCodeService.PURCHASE_ORDER_CODE_VALUE_ID;
        }
        return  codeValueId;
    }
}
