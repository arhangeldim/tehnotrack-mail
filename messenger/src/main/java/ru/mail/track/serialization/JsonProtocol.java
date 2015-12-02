package ru.mail.track.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.mail.track.message.*;
import ru.mail.track.message.Message;

/**
 *
 */
public class JsonProtocol implements Protocol {

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            return jsonMapper.writeValueAsBytes(msg);
        } catch (JsonProcessingException e) {
            throw new ProtocolException("Failed to save as json", e);
        }
    }

    @Override
    public Message decode(byte[] data) throws ProtocolException {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            Message msg = jsonMapper.readValue(data, Message.class);
            return msg;
        } catch (Exception e) {
            throw new ProtocolException("Failed to read json", e);
        }
    }
}
