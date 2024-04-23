package ru.nsu.koshevoi.lab4.model.cars.and.parts;

public abstract class Item {
    private String id;

    public Item(String id){
        this.id = id;
    }

    public String getid(){
        return this.id;
    }
}
