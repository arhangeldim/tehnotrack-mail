package ru.mail.track.serialization;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ru.mail.track.comands.CommandType;
import ru.mail.track.generics.Box;
import ru.mail.track.message.LoginMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.Protocol;
import ru.mail.track.message.SendMessage;
import sun.rmi.runtime.Log;

import static org.junit.Assert.*;

/**
 *
 */
public class MessageSerializerTest {

    private final Map<CommandType, Message> messages = new HashMap<>();

    @Before
    public void setup() {
        LoginMessage login = new LoginMessage();
        login.setSender(123L);
        login.setLogin("Jack");
        login.setPass("qwerty");
        messages.put(CommandType.USER_LOGIN, login);

        SendMessage send = new SendMessage();
        send.setChatId(1L);
        send.setMessage("Hello world!");
        messages.put(CommandType.MSG_SEND, send);

    }

    @Test
    public void encodeLogin() throws Exception {
        Message origin = messages.get(CommandType.USER_LOGIN);
        Protocol protocol = new SerializationProtocol();
        byte[] data = protocol.encode(origin);
        Message copy = protocol.decode(data);
        System.out.println(copy);

        assertTrue(copy.equals(origin));
    }


    @Test
    //@Ignore
    public void encodeSend() throws Exception {
        Message origin = messages.get(CommandType.MSG_SEND);
        Protocol protocol = new SerializationProtocol();
        byte[] data = protocol.encode(origin);
        Message copy = protocol.decode(data);
        System.out.println(copy);

        assertTrue(copy.equals(origin));
    }
}
