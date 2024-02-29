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
        q.categories
from    product as prd inner join
        inventory as itry on prd.product_id = itry.product_id inner join
        warehouse w on itry.warehouse_id = w.warehouse_id inner join
        (select prd.product_id,
                group_concat(DISTINCT cat.label ORDER BY cat.label ASC SEPARATOR ',') as categories
         from   product as prd inner join
                product_category prdc on prd.product_id = prdc.product_id inner join
                category cat on cat.category_id = prdc.category_id
         group by
             prd.product_id) as q on prd.product_id = q.product_id
group by
    prd.product_id
order by
    prd.product_id