package ru.nsu.koshevoi.lab4.model;

import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.CarWarehouse;

public class Controller extends Thread {
    private CarWarehouse carWarehouse;
    private Model model;
    public Controller(CarWarehouse carWarehouse, Model model){
        this.carWarehouse = carWarehouse;
        this.model = model
    }
    @Override
    public void run() {
        while (!isInterrupted()){
            model.setFlagForWorkers(!carWarehouse.full());
        }
    }
}
