create table if not exists `code_book` (
    code_book_id bigint primary key,
    label varchar(255) not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255)
)ENGINE=INNODB;

insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (10000, 'Country', current_timestamp(), 'System', null, null);
insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (20000, 'Provinces/State', current_timestamp(), 'System', null, null);
insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (30000, 'Order Status', current_timestamp(), 'System', null, null);
insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (40000, 'Document Type', current_timestamp(), 'System', null, null);
insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (50000, 'Order Type', current_timestamp(), 'System', null, null);

create table if not exists `code_value` (
    code_value_id bigint primary key,
    code_book_id bigint not null,
    label varchar(255) not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255),
    foreign key (code_book_id)
        references code_book (code_book_id)
        on update restrict 
        on delete cascade
) ENGINE=INNODB;

#Countries
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values (100010, 10000, 'Canada' ,current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values (100020, 10000, 'USA' ,current_timestamp(), 'System');
#Provinces and State
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200010', 20000, 'Alabama',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200020', 20000, 'Alaska',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200030', 20000, 'Arizona',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200040', 20000, 'Arkansas',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200050', 20000, 'California',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200060', 20000, 'Colorado',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200070', 20000, 'Connecticut',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200080', 20000, 'Delaware',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200090', 20000, 'Florida',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200100', 20000, 'Georgia',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200110', 20000, 'Hawaii',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200120', 20000, 'Idaho',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200130', 20000, 'Illinois',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200140', 20000, 'Indiana',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200150', 20000, 'Iowa',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200160', 20000, 'Kansas',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200170', 20000, 'Kentucky',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200180', 20000, 'Louisiana',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200190', 20000, 'Maine',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200200', 20000, 'Maryland',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200210', 20000, 'Massachusetts',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200220', 20000, 'Michigan',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200230', 20000, 'Minnesota',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200240', 20000, 'Mississippi',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200250', 20000, 'Missouri',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200260', 20000, 'Montana',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200270', 20000, 'Nebraska',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200280', 20000, 'Nevada',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200290', 20000, 'New Hampshire',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200300', 20000, 'New Jersey',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200310', 20000, 'New Mexico',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200320', 20000, 'New York',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200330', 20000, 'North Carolina',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200340', 20000, 'North Dakota',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200350', 20000, 'Ohio',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200360', 20000, 'Oklahoma',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200370', 20000, 'Oregon',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200380', 20000, 'Pennsylvania',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200390', 20000, 'Rhode Island',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200400', 20000, 'South Carolina',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200410', 20000, 'South Dakota',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200420', 20000, 'Tennessee',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200430', 20000, 'Texas',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200440', 20000, 'Utah',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200450', 20000, 'Vermont',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200460', 20000, 'Virginia',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200470', 20000, 'Washington',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200480', 20000, 'West Virginia',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200490', 20000, 'Wisconsin',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200500', 20000, 'Wyoming',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200510', 20000, 'Alberta',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200520', 20000, 'British Columbia',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200530', 20000, 'Manitoba',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200540', 20000, 'New Brunswick',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200550', 20000, 'Newfoundland and Labrador',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200560', 20000, 'Nova Scotia',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200570', 20000, 'Ontario',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200580', 20000, 'Prince Edward Island',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200590', 20000, 'Quebec',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200600', 20000, 'Saskatchewan',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200610', 20000, 'Northwest Territories',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200620', 20000, 'Nunavut',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('200630', 20000, 'Yukon',current_timestamp(), 'System');
#Order Status
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('300010', 30000, 'Pending',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('300020', 30000, 'Awaiting Fulfillment',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('300030', 30000, 'Fulfilled',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('300040', 30000, 'Completed',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('300050', 30000, 'Cancelled',current_timestamp(), 'System');
#Document Type
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('400010', 40000, 'Sales Order',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('400020', 40000, 'Purchase Orders',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('400030', 40000, 'Invoice',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('400040', 40000, 'Return Sales Order',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('400050', 40000, 'Return Purchase Order',current_timestamp(), 'System');
#Order Type
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('500010', 50000, 'Purchase Order',current_timestamp(), 'System');
insert into code_value (code_value_id, code_book_id, label,created_at, created_by) values ('500020', 50000, 'Customer Order',current_timestamp(), 'System');

create index code_value_code_book_id_idx on code_value (code_book_id);

create table  if not exists tenant (
    tenant_id bigint auto_increment primary key,
    label varchar(255) not null unique,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255)
) ENGINE=INNODB;

create table if not exists `product` (
    product_id bigint auto_increment primary key,
    tenant_id bigint not null,
    upi bigint unique,
    label varchar(255) not null,
    image_url varchar(255),
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255),
    foreign key(tenant_id)
        references tenant (tenant_id)
        on update restrict
        on delete cascade
) ENGINE=INNODB;

create index product_upi_idx on product (upi);
create index product_tenant_id_idx on product (tenant_id);

create table if not exists `category` (
    category_id bigint auto_increment primary key,
    tenant_id bigint not null,
    label varchar(255) not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255),
    foreign key(tenant_id)
        references tenant (tenant_id)
        on update restrict
        on delete cascade
) ENGINE=INNODB;

create index category_tenant_id_idx on category (tenant_id);

create table if not exists `product_category` (
    product_id bigint,
    category_id bigint,
    tenant_id bigint not null ,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255),
    primary key(product_id, category_id, tenant_id),
    foreign key(product_id)
        references product (product_id)
        on update restrict 
        on delete cascade,
    foreign key(category_id)
        references category (category_id)
        on update restrict 
        on delete cascade,
    foreign key(tenant_id)
        references tenant (tenant_id)
        on update restrict
        on delete cascade
);

create index product_category_product_id_idx on product_category (product_id);
create index product_category_category_id_idx on product_category (category_id);
create index product_category_tenant_id_idx on product_category (tenant_id);

create table if not exists `address` (
    address_id bigint primary key  auto_increment,
    tenant_id bigint not null,
    address_line_1 varchar(255) not null,
    address_line_2 varchar(255),
    city varchar(255) not null,
    province_id bigint not null,
    country_id bigint not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255),
    foreign key (tenant_id)
        references tenant (tenant_id)
        on update restrict
        on delete cascade,
    foreign key (province_id)
        references code_value (code_value_id)
        on update restrict
        on delete cascade,
    foreign key (country_id)
        references code_value (code_value_id)
        on update restrict
        on delete cascade,
    check ( country_id between 10000 and 19999),
    check ( province_id between 20000 and 29999)
)ENGINE=INNODB;

create index address_tenant_id_idx on address (tenant_id);

create table if not exists `warehouse` (
    warehouse_id bigint primary key auto_increment,
    tenant_id bigint not null,
    label varchar(255) not null,
    address_id bigint,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255),
    foreign key (tenant_id)
        references tenant (tenant_id)
        on update restrict
        on delete cascade,
    foreign key (address_id)
        references address (address_id)
        on update restrict
        on delete cascade
)ENGINE=INNODB;

create index warehouse_tenant_id_idx on warehouse (tenant_id);

create table if not exists `order` (
    order_id bigint primary key auto_increment,
    order_reference_number varchar(255) unique not null,
    tenant_id bigint not null,
    order_type bigint not null,
    order_status bigint not  null default 300010,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255),
    foreign key (order_type)
        references code_value (code_value_id)
        on update restrict
        on delete cascade,
    foreign key (order_status)
        references code_value (code_value_id)
        on update restrict
        on delete cascade,
    check ( order_type between 50000 and 59999),
    check ( order_status between 30000 and 39999)
) Engine=INNODB;

create index order_tenant_id_idx on `order` (tenant_id);
create index order_order_type_id_idx on `order` (order_type);
create index order_order_status_id_idx on `order` (order_status);

create table if not exists inventory (
    inventory_id bigint primary key auto_increment,
    product_id bigint not null,
    tenant_id bigint not null,
    sku varchar(255) not null unique,
    warehouse_id bigint not null,
    quantity bigint default 0 not null,
    minimum_quantity bigint not null,
    maximum_quantity bigint not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255),
    foreign key(product_id)
        references product (product_id)
        on update restrict
        on delete cascade,
    foreign key (tenant_id)
        references tenant (tenant_id)
        on update restrict
        on delete cascade,
    foreign key (warehouse_id)
        references warehouse (warehouse_id)
        on update restrict
        on delete cascade
)ENGINE = INNODB;

create index inventory_tenant_id_idx on inventory (tenant_id);
create index inventory_product_id_idx on inventory (product_id);
create index inventory_warehouse_id_idx on inventory (warehouse_id);

create table if not exists `order_item` (
    order_item_id bigint primary key auto_increment,
    order_id bigint not null,
    product_id bigint not null,
    tenant_id bigint not null,
    inventory_id bigint not null,
    sku varchar(255) not null,
    quantity int not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255),
    foreign key(product_id)
        references product (product_id)
        on update restrict
        on delete cascade,
    foreign key (tenant_id)
        references tenant (tenant_id)
        on update restrict
        on delete cascade,
    foreign key (order_id)
        references `order` (order_id)
        on update restrict
        on delete cascade,
    foreign key (inventory_id)
        references `inventory` (inventory_id)
        on update restrict
        on delete cascade
)ENGINE=INNODB;

create index order_item_tenant_id_idx on order_item (tenant_id);
create index order_item_order_id_idx on order_item (order_id);
create index order_item_product_id_idx on order_item (product_id);
create index order_item_inventory_id_idx on order_item (inventory_id);

create table if not exists `document` (
    document_id bigint primary key auto_increment,
    document_type bigint not null,
    tenant_id bigint not null,
    label varchar(255) not null,
    document blob,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255),
    foreign key (document_id)
        references `code_value` (code_value_id)
        on update restrict
        on delete cascade,
    foreign key (tenant_id)
        references `tenant` (tenant_id)
        on update restrict
        on delete cascade
)ENGINE=INNODB;

create index document_document_type_idx on document (document_type);

create view view_get_static_values
as
    select  cval.code_value_id, cbook.code_book_id, cval.label
    from    code_book as cbook inner join
            code_value as cval on cbook.code_book_id = cval.code_book_id
    order by
            cbook.code_book_id