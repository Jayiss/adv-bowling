drop table if exists bowling_game;
drop table if exists turn;
create table bowling_game(id INT PRIMARY KEY,
                          total_scores INT,
                          max_turn INT,
                          max_pins INT);
create table turn(id INT ,
                  foreign_id  INT ,
                  first_score INT,
                  second_score INT,
                  PRIMARY key (id,foreign_id),
                  FOREIGN KEY (foreign_id) REFERENCES bowling_game(id) on delete cascade on update cascade);


insert into bowling_game values (1001,300,10,10);
insert into turn values (1,1001,10,0);
insert into turn values (2,1001,10,0);
insert into turn values (3,1001,10,0);
insert into turn values (4,1001,10,0);
insert into turn values (5,1001,10,0);
insert into turn values (6,1001,10,0);
insert into turn values (7,1001,10,0);
insert into turn values (8,1001,10,0);
insert into turn values (9,1001,10,0);
insert into turn values (10,1001,10,0);
insert into turn values (11,1001,10,0);
insert into turn values (12,1001,10,0);