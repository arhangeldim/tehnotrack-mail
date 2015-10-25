package ru.mail.track.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


/**
 * Клиентская часть
 */
public class ThreadedClient {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";

    private InputStream in;
    private OutputStream out;
    private Socket socket;

    public ThreadedClient() {
        create();
    }

    public void create() {

        try {
            socket = new Socket(HOST, PORT);
            in = socket.getInputStream();
            out = socket.getOutputStream();

            Thread socketHandler = new SocketHandler();
            socketHandler.start();

        } catch (IOException e) {
            e.printStackTrace();
            // exit, failed to open socket
        } finally {
            IOUtil.closeQuietly(in, out, socket);
        }
    }

    /**
     *
     * Отправить сообщение в сокет
     */
    void send(String msg) throws Exception {
        out.write(msg.getBytes());
    }

    /**
     * Получено сообщение из сокета, как обрабатывать
     *
     */
    void onMessage(String msg) {
        System.out.println(msg);
    }

    class SocketHandler extends Thread {
        @Override
        public void run() {
            try {
                byte[] buf = new byte[1024 * 64];
                while (!Thread.currentThread().isInterrupted()) {
                    int read = in.read(buf);
                    String msg = new String(buf, 0, read);
                    onMessage(msg);
                }
            } catch (IOException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
        }
    }


}
