package ru.mail.track.net;

import ru.mail.track.message.Message;

/**
 * Слушает сообщения
 */
public interface MessageListener {

    void onMessage(Message message);
}
