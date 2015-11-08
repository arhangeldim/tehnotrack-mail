package ru.mail.track.net.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public class NioClient {

    static Logger log = LoggerFactory.getLogger(NioClient.class);


    public static final int PORT = 19000;

    private Selector selector;
    private SocketChannel channel;
    private ByteBuffer buffer = ByteBuffer.allocate(16);

    // TODO: Нужно создать блокирующую очередь, в которую складывать данные для обмена между потоками

    public void init() throws Exception {


        // Слушаем ввод данных с консоли
        Thread t = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    log.info("Exit!");
                    System.exit(0);
                }

                // TODO: здесь ужно сложить прочитанные данные в очередь

            }
        });
        t.start();


        selector = Selector.open();
        channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_CONNECT);

        channel.connect(new InetSocketAddress("localhost", PORT));

        while (true) {
            log.info("Waiting on select()...");
            int num = selector.select();
            log.info("Raised {} events", num);


            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey sKey = keyIterator.next();

                if (sKey.isConnectable()) {
                    log.info("[connectable]");

                    channel.finishConnect();

                    // теперь в канал можно писать
                    sKey.interestOps(SelectionKey.OP_WRITE);
                } else if (sKey.isReadable()) {
                    log.info("[readable]");

                    buffer.clear();
                    int numRead = channel.read(buffer);
                    if (numRead < 0) {
                        break;
                    }
                    log.info("From server: {}", new String(buffer.array()));

                    // теперь в канал можно писать
                    sKey.interestOps(SelectionKey.OP_WRITE);
                } else if (sKey.isWritable()) {
                    log.info("[writable]");

                    //TODO: здесь нужно вытащить данные из очереди и отдать их на сервер

                    //byte[] userInput = ...;
                    //channel.write(ByteBuffer.wrap(userInput));

                    // Ждем записи в канал
                    sKey.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        NioClient client = new NioClient();
        client.init();
    }
}
