drop table if exists game;
drop table if exists turn;

create table game(
id int not null primary key,
maxTurn int not null,
maxPin int not null
);

create table turn(
id int not null,
foreignId int not null,
firstPin int,
secondPin INTEGER,
foreign key(foreignId) references game(id),
constraint key primary key(id,foreignId)
);

insert into game values(1001,10,10);
insert into turn values(1,1001,10,null);
insert into turn values(2,1001,10,null);
insert into turn values(3,1001,10,null);
insert into turn values(4,1001,10,null);
insert into turn values(5,1001,10,null);
insert into turn values(6,1001,10,null);
insert into turn values(7,1001,10,null);
insert into turn values(8,1001,10,null);
insert into turn values(9,1001,10,null);
insert into turn values(10,1001,10,null);
insert into turn values(11,1001,10,null);
insert into turn values(12,1001,10,null);
