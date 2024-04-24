package ru.nsu.koshevoi.lab4.model.workers;

import ru.nsu.koshevoi.lab4.model.FactoryThread;
import ru.nsu.koshevoi.lab4.model.Model;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.CarWarehouse;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;

public abstract class Worker extends FactoryThread {
    protected int timeout;
    protected static Model model;
    protected CarWarehouse carWarehouse;
    public Worker(int timeout, Storage storage){
        this.timeout = timeout;
        this.carWarehouse = (CarWarehouse) storage;
    }
    public static void setModel(Model model){
        Worker.model = model;
    }
}
