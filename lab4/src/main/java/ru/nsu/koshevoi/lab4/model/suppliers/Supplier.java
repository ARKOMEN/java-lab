package ru.nsu.koshevoi.lab4.model.suppliers;

import ru.nsu.koshevoi.lab4.model.FactoryThread;
import ru.nsu.koshevoi.lab4.model.TypeOfThread;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

public abstract class Supplier extends FactoryThread {

    public TypeOfThread getType() {
        return type;
    }

    protected TypeOfThread type;

    public void setTimeout(double timeout) {
        this.timeout = timeout;
    }

    protected double timeout = 10;
    protected Storage storage;
    public Supplier(Storage storage){
        this.storage = storage;
    }

}
