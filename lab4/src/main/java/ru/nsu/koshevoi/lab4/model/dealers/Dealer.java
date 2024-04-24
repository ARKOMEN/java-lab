package ru.nsu.koshevoi.lab4.model.dealers;

import ru.nsu.koshevoi.lab4.model.FactoryThread;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.CarWarehouse;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.*;

import java.util.UUID;

public abstract class Dealer extends FactoryThread {
    protected String id;
    protected int timeout;
    protected CarWarehouse storage;
    public Dealer(int timeout, Storage storage){
        this.timeout = timeout;
        this.storage = (CarWarehouse) storage;
        UUID uuid = UUID.randomUUID();
        id = uuid.toString();
    }
}
