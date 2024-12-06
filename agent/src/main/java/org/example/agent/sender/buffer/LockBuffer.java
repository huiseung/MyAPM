package org.example.agent.sender.buffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class LockBuffer<T> implements Buffer<T> {
    private final List<T> buffer;
    private final ReentrantLock lock;

    public LockBuffer() {
        this.buffer = new LinkedList<>();
        this.lock = new ReentrantLock();
    }


    @Override
    public int addAndGetSize(T data) {
        lock.lock();
        try {
            buffer.add(data);
            return buffer.size();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<T> flush() {
        lock.lock();
        try{
            List<T> ret = new ArrayList<>(buffer);
            buffer.clear();
            return ret;
        }finally {
            lock.unlock();
        }
    }
}
