ALTER TABLE `order`
    ADD COLUMN `warehouse_id` BIGINT,
    ADD FOREIGN KEY fk_order_warehouse_id(warehouse_id) REFERENCES warehouse(warehouse_id) ON DELETE CASCADE;