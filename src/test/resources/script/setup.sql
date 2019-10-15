create table if not exists games
(
    id int,
    curTurn int
);

create table if not exists turns
(
    firstPin int,
    secondPin int,
    id int,
    foreignId int,
    foreign key(foreignId ) references games(id) on delete cascade on update cascade
);

insert into games values(1001,12);
insert into turns values
                         (10,0,1,1001),
                         (10,0,2,1001),
                         (10,0,3,1001),
                         (10,0,4,1001),
                         (10,0,5,1001),
                         (10,0,6,1001),
                         (10,0,7,1001),
                         (10,0,8,1001),
                         (10,0,9,1001),
                         (10,0,10,1001),
                         (10,0,11,1001),
                         (10,0,12,1001);



