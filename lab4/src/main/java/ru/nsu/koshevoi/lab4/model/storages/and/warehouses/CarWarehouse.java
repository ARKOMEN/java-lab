package ru.nsu.koshevoi.lab4.model.storages.and.warehouses;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Item;

public class CarWarehouse extends Storage{
    public CarWarehouse(int size){
        super(size, StorageType.Car);
    }

    @Override
    public Item get() throws InterruptedException {
        Item item = list.take();
        controller.newTask();
        return item;
    }
}
