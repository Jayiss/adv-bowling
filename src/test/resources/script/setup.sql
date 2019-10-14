create table bowlingGame(
id int not null primary key,
maxTurn int not null,
maxPin int not null
);
create table bowlingTurn(
turnId int not null,
id int not null,
firstPin int,
secondPin int,
foreign key(id) references bowlingGame(id),
constraint key primary key(turnId,id)
);
insert into bowlingGame values(1001,10,10);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(1,1001,10,null);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(2,1001,10,null);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(3,1001,10,null);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(4,1001,10,null);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(5,1001,10,null);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(6,1001,10,null);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(7,1001,10,null);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(8,1001,10,null);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(9,1001,10,null);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(10,1001,10,null);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(11,1001,10,null);
insert into bowlingTurn(turnId,id,firstPin,secondPin) values(12,1001,10,null);


