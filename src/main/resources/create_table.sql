drop table if exists customer cascade;
drop table if exists product cascade;
drop table if exists "order" cascade;

create table if not exists customer (
    id integer not null,
    fio varchar(100),   age integer,
    primary key (id)
);

create table if not exists product (
    id integer not null,
    name varchar(100),
    type varchar(100),
    primary key (id)

);


create table if not exists "order" (
    id integer not null,
    product_id integer,
    customer_id integer,
    primary key (id),
    foreign key (product_id) references product(id),
    foreign key (customer_id) references customer(id)

);