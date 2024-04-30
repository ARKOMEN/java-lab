package ru.nsu.koshevoi.lab4.model.dealers;

import ru.nsu.koshevoi.lab4.model.cars.and.parts.Car;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.CarWarehouse;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.*;

import java.time.LocalTime;


public class BMW extends Dealer{

    public BMW(int timeout, Storage storage){
        super(timeout, storage);
    }

    @Override
    public void run(){
        while(true){
            try {
                work();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void work() throws InterruptedException {
        Car car = (Car) storage.get();
        if(car != null) {
            System.out.println(LocalTime.now() + " dealer id: " + id + ": Auto " + car.getid() + " (Body: " + car.getBodyId() + ", Motor: " +
                    car.getEngineId() + ", Accessory: " + car.getAccessoryId());
        }
    }
}
