create table if not exists `order_status_change` (
   order_status_change_id  bigint primary key auto_increment,
   order_id bigint,
   order_status bigint,
   created_at timestamp not null default current_timestamp(),
   created_by varchar(255) not null,
   updated_at timestamp,
   updated_by varchar(255),
   notes varchar(255),
   foreign key (order_id)
       references `order` (order_id)
       on update restrict
       on delete cascade,
   foreign key (order_status)
       references code_value (code_value_id)
       on update restrict
       on delete cascade
) Engine=INNODB;

create index order_status_change_order_type_id_idx on `order_status_change` (order_id);
create index order_status_change_order_order_status_id_idx on `order_status_change` (order_status);