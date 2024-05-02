package ru.nsu.koshevoi.lab4.model.workers;

import ru.nsu.koshevoi.lab4.model.Model;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Accessory;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Body;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Car;
import ru.nsu.koshevoi.lab4.model.cars.and.parts.Engine;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.*;

import java.util.List;
import java.util.UUID;

public class Workman extends Worker{
    private final BodyStorage bodyStorage;
    private final EngineWarehouse engineWarehouse;
    private final AccessoriesWarehouse accessoriesWarehouse;

    int n = 0;
    public Workman(Storage outputStorage, List<Storage> inputStorage){
        super(outputStorage);
        bodyStorage = (BodyStorage) inputStorage.getFirst();
        engineWarehouse = (EngineWarehouse) inputStorage.get(1);
        accessoriesWarehouse = (AccessoriesWarehouse) inputStorage.get(2);
    }

    @Override
    public void run(){
        while(true){
            if(((ControllerCar)storage.getController()).isFlag()) {
                work();
            }
        }
    }

    private void work() {
        Body body = null;
        Engine engine = null;
        Accessory accessory = null;
        while(body == null){
            body = (Body) bodyStorage.get();
        }
        while(engine == null){
            engine = (Engine) engineWarehouse.get();
        }
        while(accessory == null){
            accessory = (Accessory) accessoriesWarehouse.get();
        }
        UUID uuid = UUID.randomUUID();
        Car car = new Car(uuid.toString());
        car.setAccessoryId(accessory.getid());
        car.setEngineId(engine.getid());
        car.setBodyId(body.getid());
        while(!storage.set(car)){
            try {
                sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
