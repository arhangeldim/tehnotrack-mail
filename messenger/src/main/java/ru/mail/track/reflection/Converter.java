package ru.mail.track.reflection;

/**
 *
 */
public interface Converter {

    byte[] convert(Object obj);
    Object convert(byte[] data);

}
