package ru.mail.track.thread.counting;

import ru.mail.track.thread.counting.Counter;

/**
 * реализация счетчика через лок
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
