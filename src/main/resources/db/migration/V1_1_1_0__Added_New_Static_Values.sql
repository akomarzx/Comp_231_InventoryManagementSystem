insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (70000, 'Group', current_timestamp(), 'System', null, null);
insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (80000, 'Role', current_timestamp(), 'System', null, null);

insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('700010', 70000, 'administrator_group',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('700020', 70000, 'sales_group',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('700030', 70000, 'purchasing_group',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('700040', 70000, 'warehouse_group',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('800010', 80000, 'administrator',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('800020', 80000, 'sales_staff',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('800030', 80000, 'purchasing_staff',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('800040', 80000, 'warehouse_staff',current_timestamp(), 'System');

