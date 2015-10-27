package ru.mail.track.thread.counting;

import ru.mail.track.thread.counting.Counter;

/**
 *
 */
public class SimpleCounter implements Counter {
    private long val;

    public long inc() {
        return val++;
    }

}
