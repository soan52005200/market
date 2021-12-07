drop table if exists customer cascade;
drop table if exists eatable cascade;
drop table if exists uneatable cascade;
drop table if exists "order" cascade;

create table if not exists customer (
    id integer not null,
    fio varchar(100),
    age integer,

    primary key (id)
);

create table if not exists eatable (
    id integer not null,
    name varchar(100),
    type varchar(100),
    bestbefore integer,

    primary key (id)

);
create table if not exists uneatable (
    id integer not null,
    name varchar(100),
    type varchar(100),
    guarantee integer,

    primary key (id)

    );


create table if not exists "order" (
    id integer not null,
    product_id integer,
    customer_id integer,

    primary key (id),
    foreign key (customer_id) references customer(id) on delete cascade

);