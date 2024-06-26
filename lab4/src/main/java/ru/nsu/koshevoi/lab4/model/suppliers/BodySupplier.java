package ru.nsu.koshevoi.lab4.model.suppliers;

import ru.nsu.koshevoi.lab4.model.TypeOfThread;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Body;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

import java.util.UUID;

public class BodySupplier extends Supplier{

    public BodySupplier(Storage storage){
        super(storage);
        type = TypeOfThread.bodySupplier;
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
        try{storage.set(body);}
        catch (Exception ignored){}
        try {
            sleep((long) (timeout * 1000L));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
