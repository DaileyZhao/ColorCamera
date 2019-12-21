package com.java.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    Lock lock=new ReentrantLock();
    public void syncMethod(){
        lock.lock();
        try {

        }finally {
            lock.unlock();
        }
    }
}
