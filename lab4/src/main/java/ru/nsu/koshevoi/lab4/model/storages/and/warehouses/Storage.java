package ru.nsu.koshevoi.lab4.model.storages.and.warehouses;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Item;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Storage {
    private final int size;
    protected Queue<Item> list;
    private final StorageType type;

    public Controller getController() {
        return controller;
    }

    protected Controller controller;
    public Storage(int size, StorageType type){
        this.size = size;
        this.list = new LinkedBlockingQueue<>(size);
        this.type = type;
    }

    public boolean set(Item item){
        return list.add(item);
    }
    public Item get() {
        return list.poll();
    }
    public boolean full(){
        return size == list.size();
    }
    public StorageType getType() {
        return type;
    }
    public int getSize(){
        return list.size();
    }

    public void setController(Controller controller){
        this.controller = controller;
    }
}
