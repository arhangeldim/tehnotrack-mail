package ru.mail.track.thread;

/**
 *
 */
public class StopThread {

    static class FlagThread extends Thread {
        private volatile boolean pleaseStop;

        @Override
        public void run() {
            while (!pleaseStop) {

            }
        }
    }

    static class InterThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {

            }
        }
    }

    public static void main(String[] args) {

    }
}
