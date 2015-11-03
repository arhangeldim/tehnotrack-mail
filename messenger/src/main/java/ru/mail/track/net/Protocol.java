package ru.mail.track.net;

import ru.mail.track.message.Message;

/**
 *
 */
public interface Protocol {

    Message decode(byte[] bytes);

    byte[] encode(Message msg);

}
