package edu.school21.chat.optional;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.*;
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
                            null// Messages
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

    @Override
    public void save(Message message) throws NotSavedSubEntityException {
        String insertQuery = "INSERT INTO MESSAGE (MessageAuthor, MessageRoom, MessageText, MessageDateTime) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, message.getAuthor().getId());
            preparedStatement.setLong(2, message.getChatroom().getId());
            preparedStatement.setString(3, message.getText());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(message.getDate().toLocalDateTime()));
            int affectedRows = preparedStatement.executeUpdate();
            SQLException e = null;
            if (affectedRows == 0) {
                throw new NotSavedSubEntityException("Failed to save message, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getLong(1));
                } else {
                    throw new NotSavedSubEntityException("Failed to save message, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new NotSavedSubEntityException("Failed to save message.");
        }
    }

    @Override
    public void update(Message message) throws NotSavedSubEntityException {
        String updateQuery = "UPDATE MESSAGE SET MessageAuthor = ?, MessageRoom = ?, MessageText = ?, MessageDateTime = ? WHERE MessageId = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setObject(1, (message.getAuthor().getId() >= 0) ? message.getAuthor().getId() : null);
            preparedStatement.setObject(2, (message.getChatroom().getId() >= 0) ? message.getChatroom().getId() : null);
            preparedStatement.setString(3, message.getText());
            preparedStatement.setTimestamp(4, (message.getDate() != null) ? message.getDate() : null);
            preparedStatement.setLong(5, message.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new NotSavedSubEntityException("Failed to update message.");
        }
    }

    private Connection getNewConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
