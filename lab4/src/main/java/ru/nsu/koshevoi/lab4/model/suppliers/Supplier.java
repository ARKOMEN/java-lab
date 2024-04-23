package ru.nsu.koshevoi.lab4.model.suppliers;

import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

public abstract class Supplier extends Thread{
    protected int timeout;
    protected Storage storage;
    protected final Object lock = new Object();
    public Supplier(int timeout, Storage storage){
        this.timeout = timeout;
        this.storage = storage;
    }

    @Override
    public void run(){}
    private void work(){}
}
