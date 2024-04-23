package ru.nsu.koshevoi.lab4.model.workers;

import ru.nsu.koshevoi.lab4.model.Model;

public abstract class Worker extends Thread{
    protected int timeout;
    protected final Object lock = new Object();
    protected Model model;
    public Worker(int timeout, Model model){
        this.timeout = timeout;
        this.model = model;
    }
    @Override
    public void run(){
        while (!isInterrupted()){
            if(model.isFlagForWorkers()) {
                work();
            }
        }
    }
    private void work(){}
}
