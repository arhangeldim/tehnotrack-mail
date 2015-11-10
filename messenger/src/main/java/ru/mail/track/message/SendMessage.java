package ru.mail.track.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ru.mail.track.comands.CommandType;

/**
 *
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="objectType")
public class SendMessage extends Message {

    @JsonProperty
    private Long chatId;

    @JsonProperty
    private String message;

    public SendMessage() {
        setType(CommandType.MSG_SEND);
    }

    public SendMessage(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//
//        if (o == null || getClass() != o.getClass()) return false;
//
//        SendMessage that = (SendMessage) o;
//
//        return new org.apache.commons.lang3.builder.EqualsBuilder()
//                .append(chatId, that.chatId)
//                .append(message, that.message)
//                .isEquals();
//    }
//
//    @Override
//    public int hashCode() {
//        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
//                .append(chatId)
//                .append(message)
//                .toHashCode();
//    }
}
