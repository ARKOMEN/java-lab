package ru.nsu.koshevoi.lab4.model.suppliers;

import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

public abstract class Supplier implements Runnable{
    protected int id;
    private int timeout;
    protected Storage storage;
    public Supplier(int timeout, Storage storage){
        this.id = 0;
        this.timeout = timeout;
        this.storage = storage;
    }
}
