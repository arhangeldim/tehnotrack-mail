package ru.mail.track.thread;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class SimpleThread {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread: started");
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println(">" + i);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread: finished");
        }
    }

    public static void main(String[] args) throws Exception {
//        start();
        join();
    }

    static void start() {
        Thread t1 = new MyThread();

        // Запуск кода в новом треде
        System.out.println("Starting thread");
        t1.start();

        // А здесь?
//        System.out.println("Running");
//        t1.run();

        System.out.println("Error:");
        // Нельзя запустить поток еще раз
        // Почему?
        //t1.start();
    }

    static void join() throws Exception {
        Thread t = new MyThread();
        System.out.println("Starting thread...");
        t.start();
        System.out.println("Joining");
        //t.join();
        System.out.println("Joined");


    }

}
