package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    public static final String ENTITY_TABLE_NAME = "Chatroom";
    private final Long id;
    private final String name;
    private final User owner;
    private List<Message> messages = null ;

    public Chatroom(Long id, String name, User owner, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public User getOwner() { return owner; }
    public List<Message> getMessages() { return messages; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Chatroom)) return false;
        Chatroom other = (Chatroom) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(name, other.name) &&
                Objects.equals(owner, other.owner) &&
                Objects.equals(messages, other.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, messages);
    }

    @Override
    public String toString() {
        return "{id = " + id + ", name = \"" + name +
                "\", owner = " + owner + ", messages = " + messages + "}";
    }
}
