package edu.school21.chat.exceptions;

import java.sql.SQLException;

public class NotSavedSubEntityException extends RuntimeException {
    public NotSavedSubEntityException (String message, SQLException e) {
        super(message);
    }
}