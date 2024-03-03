alter  table product rename column price to purchase_price;
alter table product add column selling_price decimal not null;

ALTER VIEW `view_product_summary` AS
    SELECT
        `prd`.`product_id` AS `id`,
        `prd`.`tenant_id` AS `tenant_id`,
        `prd`.`upi` AS `upi`,
        `prd`.`purchase_price`,
        `prd`.`label` AS `label`,
        `prd`.`image_url` AS `image_url`,
        `prd`.`created_at` AS `created_at`,
        `prd`.`selling_price`,
        SUM(`itry`.`quantity`) AS `quantity`,
        GROUP_CONCAT(DISTINCT `w`.`label`
                     ORDER BY `w`.`label` ASC
                     SEPARATOR ',') AS `locations`,
        `cat`.`label` AS `category_label`,
        `prd`.`sku` AS `sku`,
        `prd`.`description`
    FROM
        (((`product` `prd`
            JOIN `inventory` `itry` ON ((`prd`.`product_id` = `itry`.`product_id`)))
            JOIN `warehouse` `w` ON ((`itry`.`warehouse_id` = `w`.`warehouse_id`)))
            JOIN `category` `cat` ON ((`cat`.`category_id` = `prd`.`category_id`)))
    GROUP BY `prd`.`product_id`
    ORDER BY `prd`.`product_id`


