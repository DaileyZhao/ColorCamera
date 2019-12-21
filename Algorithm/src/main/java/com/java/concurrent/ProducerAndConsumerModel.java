package com.java.concurrent;

/**
 * 生产者和消费者模型
 * 存储空间为空时，消费者阻塞；存储空间满时，生产者阻塞。
 */
public class ProducerAndConsumerModel {

    static Integer count = 0;
    static final Integer FULL = 10;
    static String LOCK = "lock";

    public static void main(String[] args) {
        ProducerAndConsumerModel test = new ProducerAndConsumerModel();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();

    }

    class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < FULL; i++) {
                synchronized (LOCK) {
                    while (count == FULL) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println(Thread.currentThread().getName() + "Producing count=" + count);
                    LOCK.notifyAll();
                }
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < FULL; i++) {
                synchronized (LOCK) {
                    while (count == 0) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println(Thread.currentThread().getName() + "Consumer count=" + count);
                    LOCK.notifyAll();
                }
            }
        }
    }

}
