create table if not exists public.user
(
    id       serial primary key,
    name     varchar(64)  not null,
    email    varchar(64)  not null,
    password varchar(255) not null
);

-- many-to-one
create table if not exists public.item
(
    id          serial primary key,
    description text not null,
    created     timestamp,
    done        boolean,
    user_id     int references public.user (id) not null
);


create table if not exists public.category
(
    id   serial primary key,
    name varchar(64) not null
);

insert into public.category (name) values ('Срочное'), ('не срочное'), ('не обязательное');


