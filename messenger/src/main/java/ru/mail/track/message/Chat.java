package ru.mail.track.message;

import java.util.List;

/**
 *
 */
public class Chat {

    private Long id;
    /**
     * Храним список идентификаторов
     */
    private List<Long> messageIds;
    private List<Long> participantIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<Long> messageIds) {
        this.messageIds = messageIds;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

}

