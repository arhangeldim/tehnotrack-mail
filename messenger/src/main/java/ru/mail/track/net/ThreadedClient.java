package ru.mail.track.net;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import ru.mail.track.message.Message;


/**
 * Клиентская часть
 */
public class ThreadedClient implements MessageListener {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";

    ConnectionHandler handler;

    public ThreadedClient() {
        try {
            Socket socket = new Socket(HOST, PORT);
            handler = new SocketConnectionHandler(socket);

            // Этот класс будет получать уведомления от socket handler
            handler.addListener(this);

            Thread socketHandler = new Thread(handler);
            socketHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            // exit, failed to open socket
        }
    }

    public void processInput(String line) throws IOException {
        Message msg = new Message(line);
        handler.send(msg);
    }

    /**
     * Получено сообщение из handler, как обрабатывать
     *
     */
    @Override
    public void onMessage(Message msg) {
        System.out.printf("%s", msg);
    }


    public static void main(String[] args) throws Exception{
        ThreadedClient client = new ThreadedClient();

        Scanner scanner = new Scanner(System.in);
        System.out.println("$");
        while (true) {
            String input = scanner.next();
            if ("q".equals(input)) {
                return;
            }
            client.processInput(input);
        }
    }

}
