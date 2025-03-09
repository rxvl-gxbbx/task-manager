create schema if not exists tasks;

create table if not exists tasks.tasks
(
    id           uuid         not null
        primary key,
    title        varchar(255) not null,
    description  text,
    status_id    smallint,
    priority_id  smallint,
    author_id    uuid         not null,
    performer_id uuid,
    created_at   timestamp    not null,
    modified_at  timestamp    not null
);

create table if not exists tasks.users
(
    id          uuid         not null
        primary key,
    firstname   varchar(64),
    lastname    varchar(64),
    username    varchar(320) not null
        unique,
    password    varchar(128) not null,
    role        varchar(32),
    created_at  timestamp    not null,
    modified_at timestamp    not null
);

create table if not exists tasks.comments
(
    id          uuid      not null
        primary key,
    task_id     uuid      not null
        constraint comments_tasks_id_fk
            references tasks.tasks
            on delete cascade,
    comment     text      not null,
    created_at  timestamp not null,
    modified_at timestamp not null
);
