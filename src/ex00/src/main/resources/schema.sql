CREATE TABLE IF NOT EXISTS User (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Login VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    CreatedRooms VARCHAR(255),
    SocializedChatrooms VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Chatroom (
    ChatroomId INT AUTO_INCREMENT PRIMARY KEY,
    ChatroomName VARCHAR(255) NOT NULL,
    ChatroomOwner INT NOT NULL,
    FOREIGN KEY (ChatroomOwner) REFERENCES User(ID),
    Messages VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Message (
    MessageId INT AUTO_INCREMENT PRIMARY KEY,
    MessageAuthor INT NOT NULL,
    MessageRoom INT NOT NULL,
    MessageText TEXT NOT NULL,
    MessageDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (MessageAuthor) REFERENCES User(ID),
    FOREIGN KEY (MessageRoom) REFERENCES Chatroom(ChatroomId)
);
