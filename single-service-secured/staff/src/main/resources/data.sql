drop table if exists person;
drop table if exists user;
drop table if exists role;
drop table if exists user_roles;

create table person
(
    person_id bigint not null unique,
    card_id   bigint unique,
    full_name VARCHAR(255),
    in_office boolean default null
);

insert into person
values (1, 1111, 'Maksim Shelkovich', null),
       (2, 2222, 'Ilia Shelkovich', null),
       (3, 3333, 'Andrey Vorobey', null),
       (4, 4444, 'Dmitriy Chyzoy', null),
       (5, 5555, 'Petr Eremin', null),
       (6, 6666, 'Vera Sinicina', null);

create table user
(
    username    varchar(128),
    password    varchar(255),
    full_name    varchar(255)
);

create table role
(
    role    varchar(32) unique not null
);

create table user_roles
(
    user_username   varchar(128),
    roles_role       varchar(32),
    FOREIGN KEY (user_username) REFERENCES user(username),
    FOREIGN KEY (roles_role) REFERENCES role(role)
);

insert into role (role) values ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_GUEST');

insert into user (username, full_name, password) values
('admin', 'Maksim Shelkovich', '$2a$10$wr25s1MhZTM8irlDf6hlO.ig.QGurg.L7hzucv9RxacJyxP4G5ltG'),
('user', 'Ilia Shelkovich', '$2a$10$GnVX9H0H9Mdg/p7SVJTduuMUTZmva9KU4Em9OsnDyIN7nd/lzY2i2'),
('guest', '', '$2a$10$XPfSnYROEnPn9sFStRkwcO5OQgFMSbXSAJN71FqzYpOWx9ruJH1e2');

-- user - $2a$10$GnVX9H0H9Mdg/p7SVJTduuMUTZmva9KU4Em9OsnDyIN7nd/lzY2i2
-- admin - $2a$10$wr25s1MhZTM8irlDf6hlO.ig.QGurg.L7hzucv9RxacJyxP4G5ltG
-- guest - $2a$10$XPfSnYROEnPn9sFStRkwcO5OQgFMSbXSAJN71FqzYpOWx9ruJH1e2

insert into user_roles (user_username, roles_role) values ('user', 'ROLE_USER'), ('admin', 'ROLE_ADMIN'), ('guest', 'ROLE_GUEST');
