package ru.nsu.koshevoi.lab4.model.suppliers;

import ru.nsu.koshevoi.lab4.model.TypeOfThread;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Accessory;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

import java.util.UUID;

public class AccessorySupplier extends Supplier{

    public AccessorySupplier(int timeout, Storage storage){
        super(timeout, storage);
        type = TypeOfThread.accessorySupplier;
    }

    @Override
    public void run(){
        while(true){
            work();
        }
    }

    private void work() {
        UUID uuid = UUID.randomUUID();
        Accessory accessory = new Accessory(uuid.toString());
        try{storage.set(accessory);}
        catch (Exception e){}
        try {
            sleep(timeout * 100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
