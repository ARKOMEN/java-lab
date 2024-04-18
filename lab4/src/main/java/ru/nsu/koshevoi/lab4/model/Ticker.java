package ru.nsu.koshevoi.lab4.model;

public class Ticker extends Thread {
    private final Model model;

    public Ticker(Model model){
        this.model = model;
    }

    @Override
    public void run(){
        while(!isInterrupted()){
            try{
                Thread.sleep(model.getTimeout());
                model.update();
            }catch (InterruptedException e){
                break;
            }
        }
    }
}
