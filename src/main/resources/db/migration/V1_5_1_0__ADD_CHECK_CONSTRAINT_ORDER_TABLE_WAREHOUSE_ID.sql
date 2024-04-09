
ALTER TABLE `order`
    ADD constraint ck_order_warehouse_id_correct_order_type CHECK (order_type = 500020 and warehouse_id is not null);

update `code_book`
set label = 'Sales Order Status', updated_at = current_timestamp(), updated_by = 'rombao@my.centennialcollege.ca'
where `code_book_id` = 30000;

update `code_value`
set label = 'Pending', updated_at = current_timestamp(), updated_by = 'rombao@my.centennialcollege.ca'
where `code_value_id` = 300010;

update `code_value`
set label = 'Invoiced', updated_at = current_timestamp(), updated_by = 'rombao@my.centennialcollege.ca'
where `code_value_id` = 300020;

update `code_value`
set label = 'Paid', updated_at = current_timestamp(), updated_by = 'rombao@my.centennialcollege.ca'
where `code_value_id` = 300030;

update `code_value`
set label = 'Packed', updated_at = current_timestamp(), updated_by = 'rombao@my.centennialcollege.ca'
where `code_value_id` = 300040;

update `code_value`
set label = 'Shipped', updated_at = current_timestamp(), updated_by = 'rombao@my.centennialcollege.ca'
where `code_value_id` = 300050;

insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('300060', 30000, 'Closed',current_timestamp(), 'System');

insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (90000, 'Purchase Order Status', current_timestamp(), 'System', null, null);

insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('900010', 90000, 'Pending',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('900020', 90000, 'PO Sent',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('900030', 90000, 'Partially Received',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('900040', 90000, 'Recevied',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('900050', 90000, 'Closed',current_timestamp(), 'System');
