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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public class ExecutorServer implements Runnable {


    static Logger log = LoggerFactory.getLogger(ExecutorServer.class);

    public static final int PORT = 19000;

    private Selector selector;
    private ServerSocketChannel socketChannel;
    // The buffer into which we'll read data when it's available
    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);

    private BlockingQueue<SocketEvent> eventQueue = new ArrayBlockingQueue<>(10);
    private ExecutorService service = Executors.newFixedThreadPool(5);


    private Map<SocketChannel, ByteBuffer> dataToWrite = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        ExecutorServer server = new ExecutorServer();
        server.init();

        Thread t = new Thread(server);
        t.start();
    }

    public void init() throws Exception {
        selector = Selector.open();
        socketChannel = ServerSocketChannel.open();

        socketChannel.socket().bind(new InetSocketAddress(PORT));
        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        service.submit(() -> {
            try {
                SocketEvent event = eventQueue.take();
                SocketChannel channel = event.getChannel();
                ByteBuffer buffer = event.getBuffer();
                String str = new String(buffer.array());
                // CommandHandler here

                log.info("[worker] -> {}", str);

                dataToWrite.put(channel, buffer);
                SelectionKey key = channel.keyFor(selector);
                key.interestOps(SelectionKey.OP_WRITE);
                selector.wakeup();

                //write(str.getBytes());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void run() {
        while (true) {
            try {
                log.info("On select()");
                int num = selector.select();
                log.info("selected...");
                if (num == 0) {
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {

                    SelectionKey key = it.next();
                    if (key.isAcceptable()) {

                        log.info("[acceptable] {}", key.hashCode());
// Accept the connection and make it non-blocking
                        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                        Socket socket = socketChannel.socket();
                        socketChannel.configureBlocking(false);

                        // Register the new SocketChannel with our Selector, indicating
                        // we'd like to be notified when there's data waiting to be read
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        log.info("accepted on {}", socketChannel.getLocalAddress());
                    } else if (key.isReadable()) {
                        log.info("[readable {}", key.hashCode());
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        // Clear out our read buffer so it's ready for new data
                        readBuffer.clear();

                        // Attempt to read off the channel
                        int numRead = 0;
                        try {
                            numRead = socketChannel.read(readBuffer);
                        } catch (IOException e) {
                            log.error("Failed to read data from channel", e);
                            // The remote forcibly closed the connection, cancel
                            // the selection key and close the channel.
                            key.cancel();
                            socketChannel.close();
                            break;
                            //return;
                        }

                        if (numRead == -1) {
                            log.error("Failed to read data from channel (-1)");
                            // Remote entity shut the socket down cleanly. Do the
                            // same from our end and cancel the channel.
                            key.channel().close();
                            key.cancel();
                            break;
                            //return;
                        }

                        log.info("read: {}", readBuffer.toString());
                        readBuffer.flip();
//                        dataToWrite.put(socketChannel, readBuffer);
//                        key.interestOps(SelectionKey.OP_WRITE);

//
                        SocketEvent event = new SocketEvent();
                        event.setChannel(socketChannel);
                        event.setBuffer((ByteBuffer) readBuffer.flip());
                        try {
                            eventQueue.put(event);
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else if (key.isWritable()) {
                        log.info("[writable]{}", key.hashCode());
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        ByteBuffer data = dataToWrite.get(socketChannel);

                        //int n = socketChannel.write(data);
                        int n = socketChannel.write(ByteBuffer.wrap("hello".getBytes()));
                        if (data.remaining() > 0) {
                            // ... or the socket's buffer fills up
                            //break;
                        }
                        log.info("write: {}, \nnumBytes={}", new String(data.array()), n);
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
                keys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
