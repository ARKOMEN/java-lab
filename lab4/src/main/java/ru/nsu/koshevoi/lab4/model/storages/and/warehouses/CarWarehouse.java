package ru.nsu.koshevoi.lab4.model.storages.and.warehouses;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Item;

public class CarWarehouse extends Storage{
    public CarWarehouse(int size){
        super(size, StorageType.Car);
    }

    @Override
    public Item get() {
        Item item = list.poll();
        if (item != null)
            controller.newTask();
        return item;
    }
}
