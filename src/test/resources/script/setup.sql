CREATE TABLE game
(
    id      int(11),
    maxTurn int(11) NOT NULL DEFAULT '10',
    PRIMARY KEY (id)
);

CREATE TABLE turn
(
    id         int(11),
    firstPin   int(11),
    secondPin  int(11),
    foreignKey int(11),
    PRIMARY KEY (id)
);

INSERT INTO game (id, maxTurn)
VALUES ('1001', '10');

INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('1', '10', '-1', '1001');
INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('2', '10', '-1', '1001');
INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('3', '10', '-1', '1001');
INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('4', '10', '-1', '1001');
INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('5', '10', '-1', '1001');
INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('6', '10', '-1', '1001');
INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('7', '10', '-1', '1001');
INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('8', '10', '-1', '1001');
INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('9', '10', '-1', '1001');
INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('10', '10', '-1', '1001');
INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('11', '10', '-1', '1001');
INSERT INTO turn (id, firstPin, secondPin, foreignKey)
VALUES ('12', '10', '-1', '1001');