DELETE
FROM VOTES;
DELETE
FROM DISHES;
DELETE
FROM RESTAURANTS;
DELETE
FROM USER_ROLES;
DELETE
FROM USERS;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (NAME, EMAIL, PASSWORD) VALUES
('User', 'user@yandex.ru', 'password'),
('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (ROLE, USER_ID)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);

INSERT INTO RESTAURANTS (NAME)
VALUES ('KFC'),
       ('MCDonalds');

INSERT INTO DISHES (NAME, PRICE, DATE, RESTAURANT_ID)
VALUES ('Double dark burger', 25900, '2019-03-21', 100002),
       ('9 strips original', 26900, '2019-03-21', 100002),
       ('Cheefs lemonade', 8900, '2019-03-21', 100002),
       ('Big Mac Bacon', 23900, '2019-03-21', 100003),
       ('4 piece Chicken McNuggets', 14900, '2019-03-21', 100003),
       ('Pepsi', 8900, '2019-03-21', 100003),
       ('Double dark burger', 25900, '2019-03-22', 100002),
       ('9 strips original', 26900, '2019-03-22', 100002),
       ('Cheefs lemonade', 8900, '2019-03-22', 100002),
       ('Big Mac Bacon', 23900, '2019-03-22', 100003),
       ('4 piece Chicken McNuggets', 14900, '2019-03-22', 100003),
       ('Pepsi', 8900, '2019-03-22', 100003);

INSERT INTO VOTES (DATE_TIME, RESTAURANT_ID, USER_ID)
VALUES ('2019-03-21 10:00:00', 100003, 100000),
       ('2019-03-21 11:00:01', 100002, 100001);



