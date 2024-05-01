package ru.nsu.koshevoi.lab4.model;

import javafx.application.Platform;
import ru.nsu.koshevoi.lab4.model.dealers.Dealer;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.*;
import ru.nsu.koshevoi.lab4.model.suppliers.AccessorySupplier;
import ru.nsu.koshevoi.lab4.model.suppliers.BodySupplier;
import ru.nsu.koshevoi.lab4.model.suppliers.EngineSupplier;
import ru.nsu.koshevoi.lab4.model.workers.Worker;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;

public class Model {
    private Thread thread;
    private List<List<String>> inputList;
    private Map<String, Storage> storageList;
    private Controller controller;
    private ModelListener listener;
    private ExecutorService executorService;
    private double engineTimeout = 10;
    private double bodyTimeout = 10;
    private double accessoryTimeout = 10;
    private double carTimeout = 10;
    private List<FactoryThread> listOfThreads;
    private List<FactoryThread> listOfDealers;
    public boolean isFlagForWorkers() {
        return flagForWorkers;
    }

    public void setFlagForWorkers(boolean flagForWorkers) {
        this.flagForWorkers = flagForWorkers;
    }

    private boolean flagForWorkers;

    public Model(ModelListener listener) {
        flagForWorkers = true;
        this.listener = listener;
        thread = new Ticker(this);
        thread.start();
        inputList = new ArrayList<>();
        storageList = new HashMap<>();
        listOfThreads = new ArrayList<>();
        listOfDealers = new ArrayList<>();
        int numThreads = ConfigParser.parser("/home/artemiy/java-labs/java-lab/lab4/src/main/resources/config.txt", this);
        executorService = Executors.newFixedThreadPool(numThreads + 1);
        Worker.setModel(this);
        for(List<String> list : inputList){
            if(list.getFirst().contains("storages") || list.getFirst().contains("warehouse")){
                try{
                    Constructor<?> constructor = Class.forName(list.getFirst()).getDeclaredConstructor(int.class);
                    Storage object = (Storage) constructor.newInstance(Integer.parseInt(list.get(1)));
                    if (Objects.requireNonNull(object.getType()) == StorageType.Car) {
                        controller = new Controller((CarWarehouse) object, this);
                        executorService.submit(controller);
                    }
                    storageList.put(list.getFirst(), object);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                try{
                    for(int i = 0; i < Integer.parseInt(list.get(1)); i++){
                        Class<?> clazz = Class.forName(list.getFirst());
                        Constructor<?> constructor = clazz.getDeclaredConstructor(int.class, Storage.class);
                        FactoryThread object = (FactoryThread) constructor.newInstance(Integer.parseInt(list.get(3)), storageList.get(list.get(2)));
                        listOfThreads.add(object);
                        if(list.getFirst().contains("dealers")){
                            listOfDealers.add(object);
                        }
                        executorService.submit(object);
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void update(){
        try{
            Platform.runLater(() -> {
                for(FactoryThread object : listOfThreads){
                    switch (object.getType()){
                        case dealer -> ((Dealer)object).setTimeout(carTimeout);
                        case bodySupplier -> ((BodySupplier)object).setTimeout(bodyTimeout);
                        case engineSupplier -> ((EngineSupplier)object).setTimeout(engineTimeout);
                        case accessorySupplier -> ((AccessorySupplier)object).setTimeout(accessoryTimeout);
                    }
                }
                notifyMovement();
            });
        }catch (Exception ignored){}
    }

    public void stop() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(100L, TimeUnit.MILLISECONDS);
    }

    private void notifyMovement(){
        if(null != listener){
            listener.onModelChanged();
        }
    }

    public long getTimeout(){
        return 10;
    }
    public void addInputList(List<String> list) {
        this.inputList.add(list);
    }
    public Map<String, Storage> getStorageList() {
        return storageList;
    }
    public double getEngineTimeout() {
        return engineTimeout;
    }
    public double getBodyTimeout() {
        return bodyTimeout;
    }
    public double getAccessoryTimeout() {
        return accessoryTimeout;
    }
    public double getCarTimeout() {
        return carTimeout;
    }
    public void setEngineTimeout(double engineTimeout) {
        this.engineTimeout = engineTimeout;
    }
    public void setBodyTimeout(double bodyTimeout) {
        this.bodyTimeout = bodyTimeout;
    }
    public void setAccessoryTimeout(double accessoryTimeout) {
        this.accessoryTimeout = accessoryTimeout;
    }
    public void setCarTimeout(double carTimeout) {
        this.carTimeout = carTimeout;
    }
    public List<FactoryThread> getListOfDealers() {
        return listOfDealers;
    }
}
