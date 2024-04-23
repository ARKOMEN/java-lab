package ru.nsu.koshevoi.lab4.model.storages.and.warehouses;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Item;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Storage {
    private final int size;
    protected ConcurrentLinkedQueue<Item> list;
    private StorageType type;
    public Storage(int size, StorageType type){
        this.size = size;
        this.list = new ConcurrentLinkedQueue<>();
        this.type = type;
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
    public boolean empty(){
        return list.isEmpty();
    }
    public StorageType getType() {
        return type;
    }
}
