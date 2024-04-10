package ru.nsu.koshevoi.topic7.task1;

public abstract class Somebody<T> extends Thread{
    protected final Storage<T> storage;
    protected final int id;
    protected int num = 0;
    protected Info info;
    public Somebody(Storage<T> storage, int id, Info info){
        this.storage = storage;
        this.id = id;
        this.info = info;
        info.setId(id);
    }
    @Override
    public void run(){}
}
