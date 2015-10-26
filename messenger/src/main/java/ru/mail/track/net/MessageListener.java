package ru.mail.track.net;

import ru.mail.track.message.Message;

/**
 *
 */
public interface MessageListener {

    void onMessage(Message message);
}
