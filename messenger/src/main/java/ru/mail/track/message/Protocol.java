package ru.mail.track.message;


import ru.mail.track.serialization.ProtocolException;

/**
 *
 */
public interface Protocol {

    byte[] encode(Message msg) throws ProtocolException;

    Message decode(byte[] data) throws ProtocolException;
}
