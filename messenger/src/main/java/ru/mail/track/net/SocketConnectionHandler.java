package ru.mail.track.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.message.Message;
import ru.mail.track.session.Session;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс работающий с сокетом, умеет отправлять данные в сокет
 * Также слушает сокет и рассылает событие о сообщении всем подписчикам (асинхронность)
 */
public class SocketConnectionHandler implements ConnectionHandler {

    static Logger log = LoggerFactory.getLogger(SocketConnectionHandler.class);

    // подписчики
    private List<MessageListener> listeners = new ArrayList<>();
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private Protocol protocol;
    private Session session;

    public SocketConnectionHandler(Protocol protocol, Session session, Socket socket) throws IOException {
        this.protocol = protocol;
        this.socket = socket;
        this.session = session;
        session.setConnectionHandler(this);
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    @Override
    public void send(Message msg) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug(msg.toString());
        }

        // TODO: здесь должен быть встроен алгоритм кодирования/декодирования сообщений
        // то есть требуется описать протокол
        out.write(protocol.encode(msg));
        out.flush();
    }

    // Добавить еще подписчика
    @Override
    public void addListener(MessageListener listener) {
        if (listener != null) listeners.add(listener);
    }


    // Разослать всем
    public void notifyListeners(Session session, Message msg) {
        listeners.forEach(it -> it.onMessage(session, msg));
    }

    @Override
    public void run() {
        final byte[] buf = new byte[1024 * 64];
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int read = in.read(buf);
                if (read > 0) {
                    Message msg = protocol.decode(Arrays.copyOf(buf, read));
                    msg.setSender(session.getId());
                    log.info("message received: {}", msg);
                    // Уведомим всех подписчиков этого события
                    notifyListeners(session, msg);
                }
            } catch (Exception e) {
                log.error("Failed to handle connection: {}", e);
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void stop() {
        Thread.currentThread().interrupt();
    }
}


