package ru.nsu.koshevoi.lab4.model.workers;

import ru.nsu.koshevoi.lab4.model.FactoryThread;
import ru.nsu.koshevoi.lab4.model.Model;
import ru.nsu.koshevoi.lab4.model.TypeOfThread;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.CarWarehouse;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

public abstract class Worker extends FactoryThread {

    public TypeOfThread getType() {
        return type;
    }

    protected TypeOfThread type = TypeOfThread.worker;
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    protected int timeout = 10;
    protected Storage storage;
    public Worker(Storage storage){
        this.storage = storage;
    }
}
