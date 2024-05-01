package edu.school21.chat.optional;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final HikariDataSource dataSource;

    public MessagesRepositoryJdbcImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Message ID cannot be null.");
        }
        Optional<Message> result = Optional.empty();
        String query = "SELECT * FROM MESSAGE WHERE MessageId = ?";
        try (Connection connection = getNewConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
                ResultSet queryResult = statement.executeQuery();
                if (queryResult.next()) {
                    User author = new User(
                            queryResult.getLong("MessageAuthor"),
                            null, // Login
                            null, // Password
                            null, // CreatedRooms
                            null  // SocializedChatrooms
                    );
                    Chatroom chatroom = new Chatroom(
                            queryResult.getLong("MessageRoom"), // ChatroomId
                            null, // ChatroomName
                            null,
                            null  // Messages
                    );
                    result = Optional.of(new Message(
                            queryResult.getLong("MessageId"),
                            author,
                            chatroom,
                            queryResult.getString("MessageText"),
                            queryResult.getTimestamp("MessageDateTime")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while executing SQL query: " + e.getMessage());
            throw new RuntimeException("Failed to find message by ID.", e);
        }
        return result;
    }

    private Connection getNewConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
