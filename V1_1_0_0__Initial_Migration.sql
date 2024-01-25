create table if not exists code_book(
    code_book_id int primary key,
    label nvarchar(255) not null,
    created_at timestamp not null,
    created_by nvarchar(255) not null,
    updated_at timestamp,
    updated_by nvarchar(255)
)ENGINE=INNODB;

insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (100, 'Country', current_timestamp(), 'System', null, null)
insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (200, 'Provinces/State', current_timestamp(), 'System', null, null)
insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (303, 'OrderStatus', current_timestamp(), 'System', null, null)
insert into code_book (code_book_id, label, created_at, created_by, updated_at, updated_by) values (40, 'DocumentType', current_timestamp(), 'System', null, null)

create table if not exists code_value(
    code_value_id int primary key,
    code_book_id int not null,
    label nvarchar(255) not null,
    created_at timestamp not null,
    created_by nvarchar(255) not null,
    updated_at timestamp,
    updated_by nvarchar(255),
    foreign key (code_book_id)
        references code_book (code_book_id)
        on update restrict 
        on delete cascade
) ENGINE=INNODB;

create index code_value_code_book_id_idx on code_value (code_book_id);

create table if not exists product (
    product_id int auto_increment primary key,
    upi bigint unique,
    title nvarchar(255) not null,
    created_at timestamp not null,
    created_by nvarchar(255) not null,
    updated_at timestamp,
    updated_by nvarchar(255)
) ENGINE=INNODB;

create index product_upi_idx on product (upi);

create table if not exists category (
    category_id int auto_increment primary key,
    label nvarchar(255) not null
    created_at timestamp not null,
    created_by nvarchar(255) not null,
    updated_at timestamp,
    updated_by nvarchar(255)
)

create table if not exists product_category (
    product_id int,
    category_id,
    created_at timestamp not null,
    created_by nvarchar(255) not null,
    updated_at timestamp,
    updated_by nvarchar(255),
    primary key(product_id, category_id),
    foreign key(product_id)
        references product (product_id)
        on update restrict 
        on delete cascade
    foreign key(category_id)
        references category (category_id)
        on update restrict 
        on delete cascade
)

