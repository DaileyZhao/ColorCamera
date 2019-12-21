package com.java.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 只允许至多2个线程同时访问的锁
 */
public class TwinsLock implements Lock {
    private final Sync sync=new Sync(2);
    private static class Sync extends AbstractQueuedSynchronizer{
        Sync(int count){
            if (count<=0){
                throw new IllegalArgumentException();
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int i) {
            while (true){
                int current=getState();
                int newCount=current-i;
                if (newCount<0||compareAndSetState(current,newCount)){
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int i) {
            while (true){
                int current=getState();
                int newCount=current+i;
                if (compareAndSetState(current,newCount)){
                    return true;
                }
            }
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
