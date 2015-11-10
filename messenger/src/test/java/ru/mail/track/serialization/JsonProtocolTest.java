package ru.mail.track.serialization;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ru.mail.track.comands.CommandType;
import ru.mail.track.message.LoginMessage;
import ru.mail.track.message.Message;
import ru.mail.track.message.Protocol;
import ru.mail.track.message.SendMessage;

import static org.junit.Assert.*;

/**
 *
 */
public class JsonProtocolTest {

    private final Map<CommandType, Message> messages = new HashMap<>();
    private Protocol protocol;

    @Before
    public void setup() {

        protocol = new JsonProtocol();

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
    public void testLogin() throws Exception {

        Message origin = messages.get(CommandType.USER_LOGIN);
        byte[] data = protocol.encode(origin);
        Message copy = protocol.decode(data);
        assertEquals(origin, copy);
    }

    @Test(expected = ProtocolException.class)
    public void testSend() throws Exception {
        Message origin = messages.get(CommandType.MSG_SEND);
        byte[] data = protocol.encode(origin);
        Message copy = protocol.decode(data);
        assertEquals(origin, copy);
    }
 }
