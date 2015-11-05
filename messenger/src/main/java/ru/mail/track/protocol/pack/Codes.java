package ru.mail.track.protocol.pack;

/**
 *
 */
public interface Codes {

    byte INT = (byte) 0x80;
    byte LONG = (byte) 0x81;
    byte BYTE = (byte) 0x82;
    byte STR = (byte) 0x83;
    byte FALSE = (byte) 0x84;
    byte TRUE = (byte) 0x85;
    byte CHAR = (byte) 0x86;
}
