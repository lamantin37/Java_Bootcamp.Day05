package edu.school21.chat.app;


import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.optional.MessagesRepository;
import edu.school21.chat.optional.MessagesRepositoryJdbcImpl;
import edu.school21.chat.optional.UsersRepository;
import edu.school21.chat.optional.UsersRepositoryJdbcImpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Program {

    public static final String DB_URL = "jdbc:h2:~/test";
    public static final String DB_USERNAME = "sa";
    public static final String DB_PASSWORD = "";
    public static void main ( String[] args ) throws SQLException {
        HikariDataSource dataSource = configureDataSource();

        UsersRepository repository = new UsersRepositoryJdbcImpl(dataSource);

        List<User> list_us;
        list_us = repository.findAll(1, 1);
        for (User user : list_us) {
            System.out.println(user.toString());
        }
    }

    public static HikariDataSource configureDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(DB_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PASSWORD);
        return ds;
    }

}