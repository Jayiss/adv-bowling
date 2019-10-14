drop table if exists game;
drop table if exists turn;
create table game(ID INT PRIMARY KEY,
                                            MAX_TURN INT,
                         MAX_PIN INT);
create table turn(ID INT ,
                 FOREIGN_ID  INT ,
                  FIRST_PIN INT,
                  SECOND_PIN INT,
                  PRIMARY key (ID,FOREIGN_ID),
                  FOREIGN KEY (FOREIGN_ID) REFERENCES GAME(ID) on delete cascade on update cascade);


insert into game values (1001,10,10);
insert into turn values (1,1001,10,null);
insert into turn values (2,1001,10,null);
insert into turn values (3,1001,10,null);
insert into turn values (4,1001,10,null);
insert into turn values (5,1001,10,null);
insert into turn values (6,1001,10,null);
insert into turn values (7,1001,10,null);
insert into turn values (8,1001,10,null);
insert into turn values (9,1001,10,null);
insert into turn values (10,1001,10,null);
insert into turn values (11,1001,10,null);
insert into turn values (12,1001,10,null);