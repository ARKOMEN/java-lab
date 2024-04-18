package ru.nsu.koshevoi.lab4.model.storages.and.warehouses;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Item;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Storage {
    private final int size;
    private ConcurrentLinkedQueue<Item> list;
    public Storage(int size){
        this.size = size;
        this.list = new ConcurrentLinkedQueue<>();
    }
    public int getSize(){
        return size;
    }

    public void set(Item item){
        list.add(item);
    }
    public Item get(){
        return list.poll();
    }
    public boolean full(){
        return size == list.size();
    }
}
