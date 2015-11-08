package ru.mail.track.net.nio;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 */
public class BufferExample {

    public static void main(String[] args) {
        try {

            BufferExample ex = new BufferExample();
            String filePath = ex.getClass().getClassLoader().getResource("data.txt").getFile();

            Path path = Paths.get(filePath);

            // Открыть канал на файл
            FileChannel channel =  FileChannel.open(path);

            // Выделили место под буфер
            ByteBuffer buf = ByteBuffer.allocate(6);
            int bytesRead = channel.read(buf);

            // Читаем
            while (bytesRead != -1) {

                System.out.println("Read " + bytesRead);


                // здесь у нас лежат данные, но буфер сейчас в режиме записи, его нужно развернуть
                buf.flip();

                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }

                // Чистим буфер и читаем, что осталось
                buf.clear();
                bytesRead = channel.read(buf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
