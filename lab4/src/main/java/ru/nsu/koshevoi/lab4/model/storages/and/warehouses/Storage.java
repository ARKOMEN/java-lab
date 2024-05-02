package ru.nsu.koshevoi.lab4.model.storages.and.warehouses;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Item;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Storage {
    private final int size;
    protected BlockingQueue<Item> list;
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

    public void set(Item item) throws InterruptedException {
        list.put(item);
    }
    public Item get() throws InterruptedException {
        return list.take();
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
