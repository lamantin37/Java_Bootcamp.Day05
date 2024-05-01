package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    public static final String ENTITY_TABLE_NAME = "User";
    private final Long id;
    private final String login;
    private final String password;
    private final List<Chatroom> createdRooms;
    private final List<Chatroom> usedRooms;

    public User(Long id, String login, String password,
                List<Chatroom> createdRooms, List<Chatroom> usedRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.usedRooms = usedRooms;
    }

    public Long getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public List<Chatroom> getCreatedRooms() { return createdRooms; }
    public List<Chatroom> getUsedRooms() { return usedRooms; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(login, other.login) &&
                Objects.equals(password, other.password) &&
                Objects.equals(createdRooms, other.createdRooms) &&
                Objects.equals(usedRooms, other.usedRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createdRooms, usedRooms);
    }

    @Override
    public String toString() {
        return "{id = " + id + ", login = \"" + login + "\", password = \""
                + password + "\", createdRooms = " + createdRooms +
                "\", rooms = " + usedRooms + "}";
    }
}
