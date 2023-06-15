drop table if exists history cascade;
drop table if exists story cascade;

create table story
(
    id          bigint auto_increment
        primary key,
    title       varchar(255) null,
    description text         null,
    content     text         null,
    create_date datetime(6)  null
);


create table history
(
    id       bigint auto_increment
        primary key,
    story_id bigint not null,
    download bigint not null,
    view     bigint not null,
    constraint fk_history_story_story_id
        foreign key (story_id) references story (id)
);