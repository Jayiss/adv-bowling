-- create tables
drop table if exists games;
create table if not exists games
(
    game_id  int,
    max_turn int,
    max_pin  int,
    constraint games_pk
        primary key (game_id)
);

drop table if exists turns;
create table if not exists turns
(
    turn_id    int,
    game_id    int,
    first_pin  int,
    second_pin int,
    constraint turns_pk
        primary key (turn_id),
    constraint turns_games_game_id_fk
        foreign key (game_id) references games
            on delete cascade
);

-- insert games
insert into games
values (1001, 10, 10);

-- insert turns
insert into turns
values (1, 1001, 10, -1);
insert into turns
values (2, 1001, 10, -1);
insert into turns
values (3, 1001, 10, -1);
insert into turns
values (4, 1001, 10, -1);
insert into turns
values (5, 1001, 10, -1);
insert into turns
values (6, 1001, 10, -1);
insert into turns
values (7, 1001, 10, -1);
insert into turns
values (8, 1001, 10, -1);
insert into turns
values (9, 1001, 10, -1);
insert into turns
values (10, 1001, 10, -1);
insert into turns
values (11, 1001, 10, -1);
insert into turns
values (12, 1001, 10, -1);