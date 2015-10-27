package ru.mail.track.message;

/**
 *
 */
public class Message {

    private Long id;
    private String message;
    private Long sender;

    public Message(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message(Long sender, String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }


    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", sender=" + sender +
                '}';
    }
}
