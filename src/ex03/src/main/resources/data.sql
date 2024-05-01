INSERT INTO `USER` (Login, Password)
VALUES ('tomokoki', '123321'),
       ('bruscoaz', '4442321'),
       ('margartc', 'vfasf3332'),
       ('peachesl', 'fsdg22s./asd'),
       ('endeharh', 'bjr331.5Ab');

INSERT INTO CHATROOM (ChatroomName, ChatroomOwner)
VALUES ('Java', (SELECT ID FROM `USER` WHERE Login = 'tomokoki'));

INSERT INTO CHATROOM (ChatroomName, ChatroomOwner)
VALUES ('DevOps', (SELECT ID FROM `USER` WHERE Login = 'bruscoaz'));

INSERT INTO CHATROOM (ChatroomName, ChatroomOwner)
VALUES ('C', (SELECT ID FROM `USER` WHERE Login = 'endeharh'));

INSERT INTO CHATROOM (ChatroomName, ChatroomOwner)
VALUES ('Python', (SELECT ID FROM `USER` WHERE Login = 'bruscoaz'));

INSERT INTO CHATROOM (ChatroomName, ChatroomOwner)
VALUES ('Golang', (SELECT ID FROM `USER` WHERE Login = 'margartc'));

INSERT INTO MESSAGE (MessageAuthor, MessageRoom, MessageText)
VALUES ((SELECT ID FROM `USER` WHERE Login = 'tomokoki'),
        (SELECT ChatroomId FROM CHATROOM WHERE ChatroomName = 'Java'),
        'Hello world!');

INSERT INTO MESSAGE (MessageAuthor, MessageRoom, MessageText)
VALUES ((SELECT ID FROM `USER` WHERE Login = 'tomokoki'),
        (SELECT ChatroomId FROM CHATROOM WHERE ChatroomName = 'Golang'),
        'How are you?');

INSERT INTO MESSAGE (MessageAuthor, MessageRoom, MessageText)
VALUES ((SELECT ID FROM `USER` WHERE Login = 'bruscoaz'),
        (SELECT ChatroomId FROM CHATROOM WHERE ChatroomName = 'DevOps'),
        'rm -rf ./ - it`s not a trap!');

INSERT INTO MESSAGE (MessageAuthor, MessageRoom, MessageText)
VALUES ((SELECT ID FROM `USER` WHERE Login = 'endeharh'),
        (SELECT ChatroomId FROM CHATROOM WHERE ChatroomName = 'Java'),
        'good!');

INSERT INTO MESSAGE (MessageAuthor, MessageRoom, MessageText)
VALUES ((SELECT ID FROM `USER` WHERE Login = 'peachesl'),
        (SELECT ChatroomId FROM CHATROOM WHERE ChatroomName = 'Golang'),
        'All right!');
