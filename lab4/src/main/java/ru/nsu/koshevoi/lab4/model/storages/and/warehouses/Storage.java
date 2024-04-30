package ru.nsu.koshevoi.lab4.model.storages.and.warehouses;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Item;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Storage {
    private final int size;
    protected Queue<Item> list;
    private final StorageType type;
    public Storage(int size, StorageType type){
        this.size = size;
        this.list = new LinkedBlockingQueue<>(size);
        this.type = type;
    }

    public void set(Item item){
        System.out.println(list.add(item));
        System.out.println(list.size());
    }
    public Item get() {
        return list.poll();
    }
    public boolean full(){
        return size == list.size();
    }
    public synchronized boolean empty(){
        return list.isEmpty();
    }
    public StorageType getType() {
        return type;
    }
    public int getSize(){
        return list.size();
    }
}
