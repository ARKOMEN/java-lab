package ru.nsu.koshevoi.lab4.model.dealers;

import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.CarWarehouse;

public class BMW extends Dealer{

    public BMW(int timeout, CarWarehouse storage){
        super(timeout, storage);
    }
    @Override
    public void run() {
        while(storage.full()){
            try {
                this.wait(timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        synchronized (lock){
            storage.get();
            notify();
        }
    }
}
