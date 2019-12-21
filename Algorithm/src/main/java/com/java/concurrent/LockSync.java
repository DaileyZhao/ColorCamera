package com.java.concurrent;

public class LockSync {
    public synchronized void addLock() {
            System.out.println("addLock" + System.currentTimeMillis());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("addLock" + System.currentTimeMillis());
    }

    public void noLock() {
            System.out.println("noLock" + System.currentTimeMillis());
    }

    public static void main(String[] args) {
        final LockSync lockSync = new LockSync();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lockSync.addLock();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lockSync.noLock();
            }
        }).start();
    }
}
