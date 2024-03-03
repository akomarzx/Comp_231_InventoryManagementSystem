create view view_product_summary as
select  prd.product_id as id,
        prd.tenant_id,
        prd.upi,
        prd.price,
        prd.label,
        prd.image_url,
        prd.created_at,
        sum(itry.quantity) as quantity,
        group_concat( DISTINCT w.label ORDER BY w.label ASC SEPARATOR ',') as locations,
        cat.label as category_label,
        prd.sku
from    product as prd inner join
        inventory as itry on prd.product_id = itry.product_id inner join
        warehouse w on itry.warehouse_id = w.warehouse_id inner join
        category cat on cat.category_id = prd.category_id
group by
    prd.product_id
order by
    prd.product_id