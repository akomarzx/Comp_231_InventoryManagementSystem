alter table `order_item`
    drop  column `sku`;

ALTER TABLE `order_item` DROP FOREIGN KEY order_item_ibfk_1;

DROP INDEX order_item_product_id_idx ON `order_item`;

alter table `order_item`
    drop  column `product_id`;

alter table `order_item`
    RENAME COLUMN `quantity_recevied` to `quantity_processed`;
