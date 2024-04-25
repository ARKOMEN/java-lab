package ru.nsu.koshevoi.lab4.model.suppliers;

import ru.nsu.koshevoi.lab4.model.TypeOfThread;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Engine;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

import java.util.UUID;

public class EngineSupplier extends Supplier{

    public EngineSupplier(int timeout, Storage storage){
        super(timeout, storage);
        type = TypeOfThread.engineSupplier;
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
        try{storage.set(engine);}
        catch (Exception e){}
        try {
            sleep(timeout*100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
