package ru.nsu.koshevoi.lab4.model.workers;

import ru.nsu.koshevoi.lab4.model.Model;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Accessory;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Body;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Car;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Engine;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.CarWarehouse;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.StorageType;

import java.util.List;
import java.util.UUID;

public class Workman extends Worker{
    private CarWarehouse carWarehouse;
    public Workman(int timeout, Model model){
        super(timeout, model);
    }

    private void work() {
        UUID uuid = UUID.randomUUID();
        Car car = new Car(uuid.toString());
        List<Storage> list = model.getStorageList();
        for(Storage storage : list){
            switch (storage.getType()){
                case StorageType.Body -> {
                    while(storage.empty()){
                        try {
                            this.wait(timeout);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Body body = (Body) storage.get();
                    car.setBodyId(body.getid());
                }
                case StorageType.Engine -> {
                    while(storage.empty()){
                        try {
                            this.wait(timeout);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Engine engine = (Engine) storage.get();
                    car.setEngineId(engine.getid());
                }
                case StorageType.Accessories -> {
                    while(storage.empty()){
                        try {
                            this.wait(timeout);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Accessory accessory = (Accessory) storage.get();
                    car.setAccessoryId(accessory.getid());
                }
                case StorageType.Car -> carWarehouse = (CarWarehouse) storage;
            }
        }

        while(carWarehouse.full()){
            try {
                this.wait(timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock){
                carWarehouse.set(car);
                notifyAll();
            }
        }
    }
}
