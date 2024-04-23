package ru.nsu.koshevoi.lab4.model.suppliers;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Engine;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

import java.util.UUID;

public class EngineSupplier extends Supplier{

    EngineSupplier(int timeout, Storage storage){
        super(timeout, storage);
    }

    private void work() {
        UUID uuid = UUID.randomUUID();
        Engine engine = new Engine(uuid.toString());
        while(storage.full()){
            try {
                this.wait(timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        synchronized (lock){
            storage.set(engine);
            notifyAll();
        }
    }
}
