package org.group4.comp231.inventorymanagementservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.group4.comp231.inventorymanagementservice.dto.order.OrderDto;
import org.group4.comp231.inventorymanagementservice.dto.order.OrderItemDTO;
import org.group4.comp231.inventorymanagementservice.dto.account.AccountDto;
import org.group4.comp231.inventorymanagementservice.dto.order.CreateOrderDTO;
import org.group4.comp231.inventorymanagementservice.service.OrderService;
import org.group4.comp231.inventorymanagementservice.service.StaticCodeService;
import org.group4.comp231.inventorymanagementservice.utility.ValidationGroups.Create;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "/order")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "Order", description = "Endpoints for Managing Sales Order")
public class OrderController extends BaseController {

    private final OrderService orderService;
    private final StaticCodeService staticCodeService;

    public OrderController(OrderService orderService, StaticCodeService staticCodeService) {
        this.orderService = orderService;
        this.staticCodeService = staticCodeService;
    }

//    @GetMapping
//    @Operation(description = "Get All Accounts")
//    public ResponseEntity<List<AccountSummaryInfo>> getAllAccount(@RequestParam(required = false) String type) {
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create Sales/Purchase Order")
    public ResponseEntity<ObjectUtils.Null> createSalesOrder(@Validated(Create.class) @RequestBody CreateOrderDTO createOrderDTO,
                                                             @AuthenticationPrincipal(expression = "claims['email']") String createdBy) throws Exception {

        OrderDto orderDto = this.mapRequestToOrderDto(createOrderDTO);

        this.orderService.createOrder(orderDto, createdBy);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

//    @PutMapping("purchase/{id}")
//    @Operation(description = "Update Sales/Purchase Order")
//    public ResponseEntity<ObjectUtils.Null> updatePurchaseOrder(@Valid @RequestBody AccountDto dto,
//                                                          @NotNull @PathVariable("id") Long id,
//                                                          @AuthenticationPrincipal(expression = "claims['email']") String updatedBy) throws Exception {
//
//        this.orderService.updateOrder();
//        return ResponseEntity.noContent().build();
//    }

    @PutMapping("sales/{id}")
    @Operation(description = "Update Sales/Purchase Order")
    public ResponseEntity<ObjectUtils.Null> updateSalesOrder(@Valid @RequestBody AccountDto dto,
                                                        @NotNull @PathVariable("id") Long id,
                                                        @AuthenticationPrincipal(expression = "claims['email']") String updatedBy) throws Exception {

        this.orderService.updateSalesOrder();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a Sales order")
    public ResponseEntity<ObjectUtils.Null> deleteSalesOrder(@NotNull @PathVariable("id") String accountId) {
        return ResponseEntity.noContent().build();
    }

    private OrderDto mapRequestToOrderDto(CreateOrderDTO createOrderDTO) {
        Set<OrderItemDTO> orderItemDTOSet = new HashSet<>(createOrderDTO.orderItems());
        return new OrderDto(UUID.randomUUID().toString(),
                createOrderDTO.orderType(),
                createOrderDTO.accountId(),
                createOrderDTO.notes(),
                createOrderDTO.warehouseId(),
                orderItemDTOSet);
    }

}