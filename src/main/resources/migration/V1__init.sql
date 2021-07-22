create table if not exists person (
    id bigserial primary key,
    birth_date date,
    birth_place varchar(255),
    name varchar(255)
);
create table if not exists relationship (
    id bigserial primary key,
    relation_type varchar(255),
    relation_from_id bigint,
    relation_to_id bigint,
    constraint fk_relation_from foreign key (relation_from_id) references person(id),
    constraint fk_relation_to foreign key (relation_to_id) references person(id),
    unique (relation_from_id, relation_to_id, relation_type)
);

insert into person (name, birth_date, birth_place) values ('Adam', to_date('01-01-2021', 'DD-MM-YYYY'), 'Garden of Eden');
insert into person (name, birth_date, birth_place) values ('Eve', to_date('01-01-2021', 'DD-MM-YYYY'), 'Garden of Eden');
insert into relationship (relation_type, relation_from_id, relation_to_id) values ('SPOUSE', 1, 2);
insert into relationship (relation_type, relation_from_id, relation_to_id) values ('SPOUSE', 2, 1);