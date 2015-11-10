package ru.mail.track.net.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 *
 */
public class SocketEvent {
    private SocketChannel channel;
    private ByteBuffer buffer;

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }
}
