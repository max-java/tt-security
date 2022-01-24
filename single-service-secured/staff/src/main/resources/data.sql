drop table if exists department;
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

create table department
(
    department_id   bigint not null unique,
    department_name VARCHAR(255)
);

insert into department
values (1, 'Bot developing'),
       (2, 'Security Support'),
       (3, 'Java Laboratory');

create table user
(
    username  varchar(128),
    password  varchar(255),
    full_name varchar(255)
);

create table role
(
    role varchar(32) unique not null
);

create table user_roles
(
    user_username varchar(128),
    roles_role    varchar(32),
    FOREIGN KEY (user_username) REFERENCES user (username),
    FOREIGN KEY (roles_role) REFERENCES role (role)
);

insert into role (role)
values ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_GUEST');

insert into user (username, full_name, password)
values ('admin', 'Maksim Shelkovich', '$2a$10$wr25s1MhZTM8irlDf6hlO.ig.QGurg.L7hzucv9RxacJyxP4G5ltG'),
       ('user', 'Ilia Shelkovich', '$2a$10$GnVX9H0H9Mdg/p7SVJTduuMUTZmva9KU4Em9OsnDyIN7nd/lzY2i2'),
       ('guest', '', '$2a$10$XPfSnYROEnPn9sFStRkwcO5OQgFMSbXSAJN71FqzYpOWx9ruJH1e2');

-- user - $2a$10$GnVX9H0H9Mdg/p7SVJTduuMUTZmva9KU4Em9OsnDyIN7nd/lzY2i2
-- admin - $2a$10$wr25s1MhZTM8irlDf6hlO.ig.QGurg.L7hzucv9RxacJyxP4G5ltG
-- guest - $2a$10$XPfSnYROEnPn9sFStRkwcO5OQgFMSbXSAJN71FqzYpOWx9ruJH1e2

insert into user_roles (user_username, roles_role)
values ('user', 'ROLE_USER'),
       ('admin', 'ROLE_ADMIN'),
       ('guest', 'ROLE_GUEST');


--  Spring Security Access Control List
CREATE TABLE IF NOT EXISTS acl_sid
(
    id        bigint(20) NOT NULL AUTO_INCREMENT,
    principal tinyint(1)   NOT NULL,
    sid       varchar(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_1 (sid,principal)
);

-- principal: 1 - user, 2 - role
insert into acl_sid (id, principal, sid)
values (1, 1, 'user'),
       (2, 1, 'admin'),
       (3, 1, 'guest');

CREATE TABLE IF NOT EXISTS acl_class
(
    id    bigint(20) NOT NULL AUTO_INCREMENT,
    class varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_2 (class)
);
CREATE TABLE IF NOT EXISTS acl_object_identity
(
    id                 bigint(20) NOT NULL AUTO_INCREMENT,
    object_id_class    bigint(20) NOT NULL,
    object_id_identity bigint(20) NOT NULL,
    parent_object      bigint(20) DEFAULT NULL,
    owner_sid          bigint(20) DEFAULT NULL,
    entries_inheriting tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);

INSERT INTO acl_class (id, class)
VALUES (1, 'com.tutrit.tt.security.staff.bean.Person'),
       (2, 'com.tutrit.tt.security.staff.bean.Department');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES (1, 1, 1, NULL, 2, 0),
       (2, 1, 2, NULL, 2, 0),
       (3, 1, 3, NULL, 2, 0),
       (4, 1, 4, NULL, 2, 0),
       (5, 1, 5, NULL, 2, 0),
       (6, 1, 6, NULL, 2, 0),
       (7, 2, 1, NULL, 2, 0),
       (8, 2, 2, NULL, 2, 0),
       (9, 2, 3, NULL, 2, 0);

-- Access List
CREATE TABLE IF NOT EXISTS acl_entry
(
    id                  bigint(20) NOT NULL AUTO_INCREMENT,
    acl_object_identity bigint(20) NOT NULL,
    ace_order           int(11) NOT NULL,
    sid                 bigint(20) NOT NULL,
    mask                int(11) NOT NULL,
    granting            tinyint(1) NOT NULL,
    audit_success       tinyint(1) NOT NULL,
    audit_failure       tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_4 (acl_object_identity, ace_order)
);
ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity (id);
ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid (id);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (1, 3, 1, 1, 1, 1, 1, 1),
       (2, 3, 2, 2, 1, 1, 1, 1),
       (3, 3, 3, 2, 1, 1, 1, 1),
       (4, 9, 4, 1, 1, 1, 1, 1),
       (5, 9, 5, 2, 1, 1, 1, 1),
       (6, 9, 6, 2, 1, 1, 1, 1),
       (7, 9, 7, 3, 1, 1, 1, 1);

-- SELECT *
-- FROM ACL_ENTRY
--          join ACL_SID on ACL_SID.ID = ACL_ENTRY.SID
--          join ACL_OBJECT_IDENTITY on ACL_OBJECT_IDENTITY.ID = ACL_ENTRY.ACL_OBJECT_IDENTITY
--          join DEPARTMENT on DEPARTMENT.DEPARTMENT_ID = ACL_OBJECT_IDENTITY.OBJECT_ID_IDENTITY
-- where ACL_OBJECT_IDENTITY.ID = 10
--   and ACL_ENTRY.SID = 1;