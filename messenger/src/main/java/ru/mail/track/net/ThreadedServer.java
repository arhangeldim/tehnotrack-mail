package ru.mail.track.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.mail.track.message.Message;

/**
 *
 */
public class ThreadedServer implements MessageListener {

    static Logger log = LoggerFactory.getLogger(ThreadedServer.class);

    public static final int PORT = 19000;
    private volatile boolean isRunning;
    private Map<Long, ConnectionHandler> handlers = new HashMap<>();
    private AtomicLong internalCounter = new AtomicLong(0);
    private ServerSocket sSocket;

    public ThreadedServer() {
        try {
            sSocket = new ServerSocket(PORT);
            sSocket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void startServer() throws Exception {
        log.info("Started, waiting for connection");

        isRunning = true;
        while (isRunning) {
            Socket socket = sSocket.accept();
            log.info("Accepted. " + socket.getInetAddress());
            ConnectionHandler handler = new SocketConnectionHandler(socket);
            handler.addListener(this);

            handlers.put(internalCounter.incrementAndGet(), handler);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }

    public void stopServer() {
        isRunning = false;
        for (ConnectionHandler handler : handlers.values()) {
            handler.stop();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            log.info("onMessage: {}", message);
            for (ConnectionHandler handler : handlers.values()) {
                handler.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadedServer server = new ThreadedServer();
        server.startServer();
    }
}

