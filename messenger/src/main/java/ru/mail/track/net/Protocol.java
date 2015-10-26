package ru.mail.track.net;

import ru.mail.track.message.Message;

/**
 *
 */
public class Protocol {

    public static Message decode(byte[] bytes) {
        String data = new String(bytes);
        return new Message(data);
    }

    public static byte[] encode(Message msg) {
        return msg.getMessage().getBytes();

    }

}
