drop table if exists GAME;
drop table if exists TURN;
create table GAME(id INT PRIMARY KEY,
                          TURN_NUM INT,
                          max_turn INT);
create table TURN(id INT ,
                  foreign_id  INT ,
                  first_score INT,
                  second_score INT,
                  PRIMARY key (id,foreign_id),
                  FOREIGN KEY (foreign_id) REFERENCES GAME(id) on delete cascade on update cascade);


insert into GAME values (1001,300,10);
insert into TURN values (1,1001,10,0);
insert into TURN values (2,1001,10,0);
insert into TURN values (3,1001,10,0);
insert into TURN values (4,1001,10,0);
insert into TURN values (5,1001,10,0);
insert into TURN values (6,1001,10,0);
insert into TURN values (7,1001,10,0);
insert into TURN values (8,1001,10,0);
insert into TURN values (9,1001,10,0);
insert into TURN values (10,1001,10,0);
insert into TURN values (11,1001,10,0);
insert into TURN values (12,1001,10,0);