package ru.nsu.koshevoi.lab4.model.suppliers;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Accessory;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

import java.util.UUID;

public class AccessorySupplier extends Supplier{

    public AccessorySupplier(int timeout, Storage storage){
        super(timeout, storage);
    }

    @Override
    public void run(){
        while(true){
            work();
        }
    }

    void work() {
        UUID uuid = UUID.randomUUID();
        Accessory accessory = new Accessory(uuid.toString());
        while(storage.set(accessory)){
            try {
                this.wait(timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
