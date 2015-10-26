package ru.mail.track.net;

import java.io.InputStream;
import java.net.Socket;

/**
 *
 */
public class HandlerThread extends Thread {

    private Socket socket;

    public HandlerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // working with socket
    }
}
