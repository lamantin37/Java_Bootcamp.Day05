package edu.school21.chat.optional;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        List<User> result = new LinkedList<>();
        String query = "SELECT " +
                "u.ID, " +
                "u.Login, " +
                "u.Password, " +
                "GROUP_CONCAT(CR.ChatroomId) AS CreatedRooms " +
                "FROM `USER` u " +
                "LEFT JOIN CHATROOM CR ON u.ID = CR.ChatroomOwner " +
                "GROUP BY u.ID, u.Login, u.Password " +
                "LIMIT ?, ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, page * size);
            preparedStatement.setInt(2, size);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long userId = resultSet.getLong("ID");
                String login = resultSet.getString("Login");
                String password = resultSet.getString("Password");
                List<Chatroom> createdRooms = new LinkedList<>();

                String[] createdRoomIds = resultSet.getString("CreatedRooms").split(",");

                for (String createdRoomId : createdRoomIds) {
                    if (!createdRoomId.isEmpty()) {
                        createdRooms.add(new Chatroom(Long.parseLong(createdRoomId), null, null, null));
                    }
                }

                result.add(new User(userId, login, password, createdRooms, new ArrayList<>()));
            }
        } catch (SQLException e) {
            throw new NotSavedSubEntityException("Bad Query");
        }
        return result;
    }
}
