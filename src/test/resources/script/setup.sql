create table game(id int,primary key (id));
create table turns(id int,gameId int,first int not null ,second int, primary key (id,gameId),
foreign key (gameId) references game(id));
insert into game values (1001);
insert into turns values (1,1001,10,null );
insert into turns values (2,1001,10,null);
insert into turns values (3,1001,10,null);
insert into turns values (4,1001,10,null);
insert into turns values (5,1001,10,null);
insert into turns values (6,1001,10,null);
insert into turns values (7,1001,10,null);
insert into turns values (8,1001,10,null);
insert into turns values (9,1001,10,null);
insert into turns values (10,1001,10,null);
insert into turns values (11,1001,10,null);
insert into turns values (12,1001,10,null);