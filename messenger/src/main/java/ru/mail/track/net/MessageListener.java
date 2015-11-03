package ru.mail.track.net;

import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

/**
 * Слушает сообщения
 */
public interface MessageListener {

    void onMessage(Session session, Message message);
}
