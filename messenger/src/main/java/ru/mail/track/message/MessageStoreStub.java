package ru.mail.track.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class MessageStoreStub implements MessageStore {

    public static final AtomicLong counter = new AtomicLong(0);

    List<SendMessage> messages1 = Arrays.asList(
            new SendMessage(1L, "msg1_1"),
            new SendMessage(1L, "msg1_2"),
            new SendMessage(1L, "msg1_3"),
            new SendMessage(1L, "msg1_4"),
            new SendMessage(1L, "msg1_5")
    );
    List<SendMessage> messages2 = Arrays.asList(
            new SendMessage(2L, "msg2_1"),
            new SendMessage(2L, "msg2_2"),
            new SendMessage(2L, "msg2_3"),
            new SendMessage(2L, "msg2_4"),
            new SendMessage(2L, "msg2_5")
    );

    Map<Long, Message> messages = new HashMap<>();

    static Map<Long, Chat> chats = new HashMap<>();

    static {
        Chat chat1 = new Chat();
        chat1.addParticipant(0L);
        chat1.addParticipant(2L);

        Chat chat2 = new Chat();
        chat2.addParticipant(1L);
        chat2.addParticipant(2L);
        chat2.addParticipant(3L);

        chats.put(1L, chat1);
        chats.put(2L, chat2);
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) {
        return null;
    }

    @Override
    public Chat getChatById(Long chatId) {
        return chats.get(chatId);
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) {
        return chats.get(chatId).getMessageIds();
    }

    @Override
    public Message getMessageById(Long messageId) {
        return messages.get(messageId);
    }

    @Override
    public void addMessage(Long chatId, Message message) {
        message.setId(counter.getAndIncrement());
        chats.get(chatId).addMessage(message.getId());
        messages.put(message.getId(), message);
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) {

    }
}
