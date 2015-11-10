package ru.mail.track.message;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ru.mail.track.comands.CommandType;

/**
 *
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonSubTypes({
        @JsonSubTypes.Type(value=LoginMessage.class),
})
public class Message implements Serializable {

    private Long id;
    private Long sender;

    private CommandType type;

    public Message() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                ", sender=" + sender +
                ", type=" + type +
                '}';
    }
}
