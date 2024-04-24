package ru.nsu.koshevoi.lab4.model.suppliers;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Engine;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

import java.util.UUID;

public class EngineSupplier extends Supplier{

    public EngineSupplier(int timeout, Storage storage){
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
        Engine engine = new Engine(uuid.toString());
        while(storage.set(engine)){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
