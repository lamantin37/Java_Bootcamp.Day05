package edu.school21.chat.app;

import edu.school21.chat.optional.MessagesRepositoryJdbcImpl;
import edu.school21.chat.models.Message;
import com.zaxxer.hikari.HikariDataSource;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static final String DB_URL = "jdbc:h2:~/test";
    public static final String DB_USERNAME = "sa";
    public static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        System.out.println("Enter a message ID: ");
        try (Scanner inp = new Scanner(System.in)) {
            Long id = inp.nextLong();
            try (HikariDataSource dataSource = configureDataSource()) {
                MessagesRepositoryJdbcImpl messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
                printQueryResult(messagesRepository.findById(id), id);
            }
        } catch (InputMismatchException e) {
            System.out.println("Wrong id format");
            System.exit(-1);
        }
    }

    public static HikariDataSource configureDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(DB_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PASSWORD);
        return ds;
    }

    public static void printQueryResult(Optional<Message> message, Long id) {
        System.out.println(message.isPresent() ? message.get() : "Message with id = " + id + " does not exist");
    }
}
