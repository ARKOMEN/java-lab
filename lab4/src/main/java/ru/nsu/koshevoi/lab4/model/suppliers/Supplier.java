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

    protected double timeout;
    protected Storage storage;
    public Supplier(int timeout, Storage storage){
        this.timeout = timeout;
        this.storage = storage;
    }

}
