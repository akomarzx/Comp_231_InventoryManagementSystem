package org.group4.comp231.inventorymanagementservice.domain;

public enum OrderStatus {

    SALES_ORDER_PENDING(300010L),
    SALES_ORDER_INVOICED(300020L),
    SALES_ORDER_PAID(300030L),
    SALES_ORDER_PACKED(300040L),
    SALES_ORDER_SHIPPED(300050L),
    SALES_ORDER_CLOSED(300060L),
    PURCHASE_ORDER_PENDING(900010L),
    PURCHASE_ORDER_PO_SENT(900020L),
    PURCHASE_ORDER_PARTIALLY_RECEVIED(900030L),
    PURCHASE_ORDER_RECEIVED(900040L),
    PURCHASE_ORDER_CLOSED(900050L);

    private Long code;

    private OrderStatus(Long code) {
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

}
