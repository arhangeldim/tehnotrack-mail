package ru.mail.track.thread;

/**
 *
 */
public class LockCounter implements Counter {

    private long counter;

    public synchronized long inc() {
        return counter++;
    }

    public long incUnsafe() {
        return counter++;
    }


}
