package ru.mail.track.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.mail.track.message.Message;

/**
 *
 */
public class SocketConnectionHandler implements ConnectionHandler {

    static Logger log = LoggerFactory.getLogger(SocketConnectionHandler.class);

    private List<MessageListener> listeners = new ArrayList<>();
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public SocketConnectionHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    @Override
    public void send(Message msg) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug(msg.toString());
        }

        // TODO: здесь долен быть встроен алгоритм кодирования/декодирования сообщений
        // то есть требуется описать протокол
        out.write(Protocol.encode(msg));
        out.flush();
    }

    @Override
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners(Message msg) {
        listeners.forEach(it -> it.onMessage(msg));
    }

    @Override
    public void run() {
        final byte[] buf = new byte[1024 * 64];
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int read = in.read(buf);
                if (read > 0) {
                    Message msg = Protocol.decode(Arrays.copyOf(buf, read));

                    log.info("message received: {}", msg);

                    notifyListeners(msg);
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


