package ru.nsu.koshevoi.lab4.model.cars.and.parts;

public class Car extends Item{
    public String getEngineId() {
        return EngineId;
    }

    public String getAccessoryId() {
        return AccessoryId;
    }

    public String getBodyId() {
        return bodyId;
    }

    public void setBodyId(String bodyId) {
        this.bodyId = bodyId;
    }

    public void setAccessoryId(String accessoryId) {
        AccessoryId = accessoryId;
    }

    public void setEngineId(String engineId) {
        EngineId = engineId;
    }

    private String bodyId;
    private String AccessoryId;
    private String EngineId;
    public Car(String id){
        super(id);
    }
}
