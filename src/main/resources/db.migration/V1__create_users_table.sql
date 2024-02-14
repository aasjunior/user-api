create schema if not exists users;

create table users.users(
    id bigserial primary key,
    name varchar(255) not null,
    username varchar(50) unique not null,
    email varchar(50) unique not null,
    password varchar(255) not null,
    role varchar(10) not null,
    registrationDate timestamp not null
);