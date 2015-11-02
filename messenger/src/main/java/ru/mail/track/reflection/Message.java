package ru.mail.track.reflection;

/**
 *
 */
public class Message {
    Integer id;
    Integer chatId;
    String body;


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", body='" + body + '\'' +
                '}';
    }
}
