package org.group4.comp231.inventorymanagementservice.service;

import jakarta.transaction.Transactional;
import org.group4.comp231.inventorymanagementservice.domain.OrderStatusChange;
import org.group4.comp231.inventorymanagementservice.config.TenantIdentifierResolver;
import org.group4.comp231.inventorymanagementservice.domain.*;
import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeBook;
import org.group4.comp231.inventorymanagementservice.domain.static_code.CodeValue;
import org.group4.comp231.inventorymanagementservice.dto.order.OrderDto;
import org.group4.comp231.inventorymanagementservice.dto.order.OrderItemDTO;
import org.group4.comp231.inventorymanagementservice.mapper.order.OrderMapper;
import org.group4.comp231.inventorymanagementservice.repository.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    @Transactional
    public void updateSalesOrder() {

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
                return Stream.of(OrderStatus.values())
                        .filter(c -> c.getCode().equals(valList.get(index + 1).getId()))
                        .findFirst()
                        .orElseThrow(IllegalArgumentException::new);
            }
        }
    }
}
