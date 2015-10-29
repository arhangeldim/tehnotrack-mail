package ru.mail.track.message;

import ru.mail.track.comands.CommandType;

/**
 *
 */
public class SendMessage extends Message {

    private Long chatId;
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
}
