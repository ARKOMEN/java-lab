package ru.nsu.koshevoi.lab4.model.suppliers;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Body;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

import java.util.UUID;

public class BodySupplier extends Supplier{

    public BodySupplier(int timeout, Storage storage){
        super(timeout, storage);
    }

    @Override
    public void run(){
        while(true){
            work();
        }
    }

    private void work() {
        UUID uuid = UUID.randomUUID();
        Body body = new Body(uuid.toString());
        while(storage.set(body)){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
