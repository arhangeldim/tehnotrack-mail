package ru.mail.track.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class SimpleServer implements Runnable {

    private Selector selector;
    private ServerSocketChannel socketChannel;
    // The buffer into which we'll read data when it's available
    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);

    public void init() throws Exception {
        selector = Selector.open();
        socketChannel = ServerSocketChannel.open();

        socketChannel.socket().bind(new InetSocketAddress(9999));
        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    @Override
    public void run() {
        while (true) {
            try {
                int num = selector.select();
                if (num == 0) {
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {

                    SelectionKey key = it.next();
                    if (key.isAcceptable()) {
// Accept the connection and make it non-blocking
                        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                        Socket socket = socketChannel.socket();
                        socketChannel.configureBlocking(false);

                        // Register the new SocketChannel with our Selector, indicating
                        // we'd like to be notified when there's data waiting to be read
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        // Clear out our read buffer so it's ready for new data
                        readBuffer.clear();

                        // Attempt to read off the channel
                        int numRead;
                        try {
                            numRead = socketChannel.read(readBuffer);
                        } catch (IOException e) {
                            // The remote forcibly closed the connection, cancel
                            // the selection key and close the channel.
                            key.cancel();
                            socketChannel.close();
                            return;
                        }

                        if (numRead == -1) {
                            // Remote entity shut the socket down cleanly. Do the
                            // same from our end and cancel the channel.
                            key.channel().close();
                            key.cancel();
                            return;
                        }

                        // Hand the data off to our worker thread
                        //worker.processData(this, socketChannel, this.readBuffer.array(), numRead);

                    }
                }
                keys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
