package ru.mail.track.message;

/**
 *
 */
public interface Protocol {

    byte[] encode(Message msg);

    Message decode(byte[] data);
}
