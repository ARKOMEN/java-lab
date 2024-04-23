package ru.nsu.koshevoi.lab4.model.dealers;

import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.CarWarehouse;

public abstract class Dealer implements Runnable{
    protected final Object lock = new Object();
    protected int timeout;
    protected CarWarehouse storage;
    public Dealer(int timeout, CarWarehouse storage){
        this.timeout = timeout;
        this.storage = storage;
    }
}
