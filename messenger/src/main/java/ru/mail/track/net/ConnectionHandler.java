package ru.mail.track.net;

import java.io.IOException;

import ru.mail.track.message.Message;

/**
 * Обработчик сокета
 */
public interface ConnectionHandler extends Runnable {

    void send(Message msg) throws IOException;

    void addListener(MessageListener listener);

    void stop();
}
