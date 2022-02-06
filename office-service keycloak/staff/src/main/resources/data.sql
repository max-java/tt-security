drop table if exists person;
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