package ru.nsu.koshevoi.lab4.model.suppliers;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Accessory;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

public class AccessorySupplier extends Supplier{

    public AccessorySupplier(int timeout, Storage storage){
        super(timeout, storage);
    }
    @Override
    public void run() {
        Accessory accessory = new Accessory(id);
        id++;
        while(storage.full()){
            this
        }
    }
}
