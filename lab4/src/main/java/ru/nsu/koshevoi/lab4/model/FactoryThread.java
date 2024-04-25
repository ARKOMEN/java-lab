package ru.nsu.koshevoi.lab4.model;

public abstract class FactoryThread extends Thread{
    public TypeOfThread getType() {
        return null;
    }
    public void setTimeout(int timeout) {}
}
