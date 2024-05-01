package edu.school21.chat.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {
    public static final String ENTITY_TABLE_NAME = "Message";
    private final Long id;
    private final User author;
    private final Chatroom chatroom;
    private final String text;
    private final Timestamp date;

    public Message(Long id, User author, Chatroom chatroom,
                   String text, Timestamp date) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        this.date = date;
    }

    public Long getId() { return id; }
    public User getAuthor() { return author; }
    public Chatroom getChatroom() { return chatroom; }
    public String getText() { return text; }
    public Timestamp getDate() { return date; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Message)) return false;
        Message other = (Message) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(author, other.author) &&
                Objects.equals(chatroom, other.chatroom) &&
                Objects.equals(text, other.text) &&
                Objects.equals(date, other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, chatroom, text, date);
    }

    @Override
    public String toString() {
        return "{id = " + id + ", author = " + author + ", room = " + chatroom +
                ", text = \"" + text + "\", dateTime = " + date + "}";
    }
}
