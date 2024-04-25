package ru.nsu.koshevoi.lab4.model.workers;

import ru.nsu.koshevoi.lab4.model.Model;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Accessory;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Body;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Car;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Engine;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Workman extends Worker{
    private final BodyStorage bodyStorage;
    private final EngineWarehouse engineWarehouse;
    private final AccessoriesWarehouse accessoriesWarehouse;

    public Workman(int timeout, Storage storage){
        super(timeout, storage);
        Map<String, Storage> map = model.getStorageList();
        bodyStorage = (BodyStorage) map.get("ru.nsu.koshevoi.lab4.model.storages.and.warehouses.BodyStorage");
        engineWarehouse = (EngineWarehouse) map.get("ru.nsu.koshevoi.lab4.model.storages.and.warehouses.EngineWarehouse");
        accessoriesWarehouse = (AccessoriesWarehouse) map.get("ru.nsu.koshevoi.lab4.model.storages.and.warehouses.AccessoriesWarehouse");
    }

    @Override
    public void run(){
        while(true){
            if(model.isFlagForWorkers()) {
                work();
            }
        }
    }

    private void work() {
        Body body = (Body) bodyStorage.get();
        Engine engine = (Engine) engineWarehouse.get();
        Accessory accessory = (Accessory) accessoriesWarehouse.get();
        if(body != null && engine != null && accessory != null) {
            UUID uuid = UUID.randomUUID();
            Car car = new Car(uuid.toString());
            car.setAccessoryId(accessory.getid());
            car.setEngineId(engine.getid());
            car.setBodyId(body.getid());
            carWarehouse.set(car);
        }
    }
}
