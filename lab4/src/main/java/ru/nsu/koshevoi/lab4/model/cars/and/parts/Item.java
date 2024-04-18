package ru.nsu.koshevoi.lab4.model.cars.and.parts;

public abstract class Item {
    private int id;

    public Item(int id){
        this.id = id;
    }

    public int getid(){
        return this.id;
    }
}
