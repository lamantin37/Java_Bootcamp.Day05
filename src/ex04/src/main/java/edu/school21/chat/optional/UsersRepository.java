package edu.school21.chat.optional;

import edu.school21.chat.models.User;

import java.util.List;

public interface UsersRepository {
    List<User> findAll(int page, int size);
}
