package ru.mail.track.protocol.pack;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class MessagePack implements Codes {
    public static final int HEADER_SIZE = 10;

    int limit;
    ByteBuffer byteBuffer;

    public MessagePack(int capacity) {
        limit = capacity;
        byteBuffer = ByteBuffer.allocate(capacity);
    }

    public MessagePack(byte[] data) {
        byteBuffer = ByteBuffer.allocate(data.length);
        byteBuffer.put(data);
        byteBuffer.flip();
    }

    public void packByte(byte val) {
        byteBuffer.put(val);
    }

    public byte unpackByte() {
        return byteBuffer.get();
    }

    public void packInt(int val) {
        byteBuffer.putInt(val);
    }

    public int unpackInt() {
        return byteBuffer.getInt();
    }

    public void packLong(long val) {
        byteBuffer.putLong(val);
    }

    public long unpackLong() {
        return byteBuffer.getLong();
    }

    public void packStr(String val) {
        byteBuffer.putInt(val.length());
        final byte[] data = val.getBytes();
        byteBuffer.put(data);
    }

    public String unpackStr() {
        int length = unpackInt();
        final byte[] data = new byte[length];
        byteBuffer.get(data);
        return new String(data);
    }



    public int reset() {
        int pos= byteBuffer.position();
        byteBuffer.flip();
        return pos;
    }

    public byte[] getData() {
        return byteBuffer.array();
    }

    public void pack(Class cls, Object obj) {

        Map<Class<?>, Packer> map = new HashMap<>();
        map.put(Integer.class, p -> packInt((Integer) obj));
        map.put(Long.class, p -> packLong((Long) obj));
        map.put(String.class, p -> packStr((String) obj));

        if (map.get(cls) == null) {
            System.out.println("Failed to get converter for: " + cls.getName());
        }
        map.get(cls).pack(obj);

    }

    public Object unpack(Class<?> cls) {

        Map<Class<?>, Unpacker<?>> map = new HashMap<>();
        map.put(Integer.class, this::unpackInt);
        map.put(Long.class, this::unpackLong);
        map.put(String.class, this::unpackStr);

        if (map.get(cls) == null) {
            System.out.println("Failed to get converter for: " + cls.getName());
        }
        return map.get(cls).unpack();

    }

    interface Packer {
        void pack(Object obj);
    }

    interface Unpacker<T> {
        T unpack();
    }

}
