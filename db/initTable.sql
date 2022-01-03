create table if not exists public.item
(
    id serial primary key,
    description text,
    created timestamp,
    done boolean
);