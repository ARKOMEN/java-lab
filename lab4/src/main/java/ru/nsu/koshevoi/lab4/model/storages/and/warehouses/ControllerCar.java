package ru.nsu.koshevoi.lab4.model.storages.and.warehouses;

import ru.nsu.koshevoi.lab4.model.Model;

public class ControllerCar implements Controller {
    private final CarWarehouse carWarehouse;

    public boolean isFlag() {
        return flag;
    }

    volatile private boolean flag = true;
    public ControllerCar(Storage storage){
        this.carWarehouse = (CarWarehouse) storage;
    }

    @Override
    public void newTask(){
        flag = !carWarehouse.full();
    }
}
