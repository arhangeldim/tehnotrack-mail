package ru.mail.track.integr;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.track.message.Message;
import ru.mail.track.message.SendMessage;
import ru.mail.track.net.MessageListener;
import ru.mail.track.net.ThreadedClient;
import ru.mail.track.net.ThreadedServer;
import ru.mail.track.session.Session;

public class IntegrTest implements MessageListener {

    private ThreadedClient client;
    private String result;

    @Before
    public void setup() throws Exception {
        new Thread(() -> {
            ThreadedServer.main(null);
        }).start();
        Thread.sleep(100);
        client = ThreadedClient.start();
        client.getHandler().addListener(this);
    }

    @Test
    public void login() throws Exception {
//        assert/*that*/(gotResult(new SendMessage(-1L, "Login Ok"),/*on request*/"login A 1"));
        assert/*that*/(gotResult(new SendMessage(-1L, "Login Failed"),/*on request*/"login A 11"));
    }

    private boolean gotResult(Object result, String on) throws Exception {
        client.processInput(on);
        Thread.sleep(100);
        return this.result.equals(result.toString());
    }

    @Override
    public void onMessage(Session session, Message message) {
        result = message.toString();
//        System.out.println(result);
    }

    @After
    public void close() {
        //todo
    }
}
