package ru.nsu.koshevoi.topic7.task1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Storage<T> {
    private final BlockingQueue<T> queue;
    private int num;

    public Storage(int capacity){
        this.queue = new LinkedBlockingDeque<>(capacity);
    }

    public void put(T item) throws InterruptedException {
        queue.put(item);
    }

    public T get() throws InterruptedException{
        num++;
        return queue.take();
    }

    public int size(){
        return queue.size();
    }

    public int getNum(){
        return num;
    }
}
