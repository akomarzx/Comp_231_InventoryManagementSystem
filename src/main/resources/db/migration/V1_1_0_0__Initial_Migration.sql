create table if not exists code_book(
    code_book_id int primary key,
    label varchar(255) not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255)
)ENGINE=INNODB ;

insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (100, 'Country', current_timestamp(), 'System', null, null);
insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (200, 'Provinces/State', current_timestamp(), 'System', null, null);
insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (300, 'OrderStatus', current_timestamp(), 'System', null, null);
insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (400, 'DocumentType', current_timestamp(), 'System', null, null);

create table if not exists code_value(
    code_value_id int primary key,
    code_book_id int not null,
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

create index code_value_code_book_id_idx on code_value (code_book_id);

create table  if not exists tenant (
    tenant_id int auto_increment primary key,
    tenant_name varchar(255) not null unique,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255)
) ENGINE=INNODB;

create table if not exists product (
    product_id int auto_increment primary key,
    tenant_id int not null,
    upi bigint unique,
    title varchar(255) not null,
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

create table if not exists category (
    category_id int auto_increment primary key,
    tenant_id int not null,
    label varchar(255) not null,
    created_at timestamp not null,
    created_by varchar(255) not null,
    updated_at timestamp,
    updated_by varchar(255),
    foreign key(tenant_id)
        references tenant (tenant_id)
        on update restrict
        on delete cascade
);

create index category_tenant_id_idx on category (tenant_id);

create table if not exists product_category (
    product_id int,
    category_id int,
    tenant_id int not null ,
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

