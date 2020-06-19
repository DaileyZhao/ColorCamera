package com.java.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class FairLockDemo implements Runnable {
    private int sumnumber = 0;
    private static ReentrantLock lock = new ReentrantLock(true);


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "未获得锁，sumnunber is " + sumnumber);
        while (sumnumber < 3) {
            try {
                lock.lock();
                sumnumber++;
                System.out.println(Thread.currentThread().getName() + "获得锁，sumnunber is " + sumnumber);
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放锁，sumnunber is " + sumnumber);
            }
        }
        System.out.println(Thread.currentThread().getName() + "退出循环，sumnunber is " + sumnumber);
    }

    public static void main(String[] args) {
        FairLockDemo fairLockDemo=new FairLockDemo();
        Thread thread1=new Thread(fairLockDemo);
        Thread thread2=new Thread(fairLockDemo);
        Thread thread3=new Thread(fairLockDemo);
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
