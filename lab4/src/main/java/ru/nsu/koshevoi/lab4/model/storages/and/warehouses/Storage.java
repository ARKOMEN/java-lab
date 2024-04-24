package ru.nsu.koshevoi.lab4.model.storages.and.warehouses;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Item;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Storage {
    private final int size;
    protected ConcurrentLinkedQueue<Item> list;
    private final StorageType type;
    public Storage(int size, StorageType type){
        this.size = size;
        this.list = new ConcurrentLinkedQueue<>();
        this.type = type;
    }

    public synchronized boolean set(Item item){
        boolean flag = !list.add(item);
        if(!flag){
            //System.out.println(type);
            notifyAll();
        }
        return flag;
    }
    public synchronized Item get() throws InterruptedException {
        while (empty()){
            wait();
        }
        Item item = list.poll();
        notifyAll();
        return item;
    }
    public synchronized boolean full(){
        return size == list.size();
    }
    public synchronized boolean empty(){
        return list.isEmpty();
    }
    public StorageType getType() {
        return type;
    }
}
