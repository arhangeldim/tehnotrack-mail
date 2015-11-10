package ru.mail.track.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import ru.mail.track.message.Message;
import ru.mail.track.message.Protocol;

/**
 *
 */
public class SerializationProtocol implements Protocol {

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {

            out.writeObject(msg);
            byte[] objData = bos.toByteArray();
            int size = objData.length;

            ByteBuffer buf = ByteBuffer.allocate(size + 4);
            buf.putInt(size);
            buf.put(objData);

            return buf.array();

        } catch (IOException e) {
            throw new ProtocolException("Failed to encode message", e);
        }
    }

    @Override
    public Message decode(byte[] data) throws ProtocolException {
        ByteBuffer buf = ByteBuffer.wrap(data);
        int size = buf.getInt();
        if (size != data.length - 4) {
            throw new ProtocolException(String.format("Invalid data. Expected size: %d, actual size: %d", size, data.length));
        }
        byte[] objData = new byte[size];
        buf.get(objData);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(objData);
             ObjectInput in = new ObjectInputStream(bis)) {
            return (Message) in.readObject();
        } catch (IOException e) {
            throw new ProtocolException("Failed to decode messsage", e);
        } catch (ClassNotFoundException e) {
            throw new ProtocolException("No class found",  e);
        }
    }
}
