package ru.nsu.koshevoi.lab4.model;

import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.*;
import ru.nsu.koshevoi.lab4.model.workers.Worker;
import ru.nsu.koshevoi.lab4.model.workers.Workman;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {
    private long timeout = 200;
    private Thread thread;
    private List<List<String>> listStoragesForParse;
    private List<List<String>> suppliers;
    private List<List<String>> workers;
    private List<List<String>> dealers;
    private List<List<String>> other;
    private Map<String, Storage> storageList;
    private Controller controller;
    private ModelListener listener;
    private ExecutorService executorService;

    public boolean isFlagForWorkers() {
        return flagForWorkers;
    }

    public void setFlagForWorkers(boolean flagForWorkers) {
        this.flagForWorkers = flagForWorkers;
    }

    private boolean flagForWorkers;
    public static void main(String[] args) {
        new Model();
    }

    public Model() {
        flagForWorkers = true;
        thread = new Ticker(this);
        thread.start();
        listStoragesForParse = new ArrayList<>();
        suppliers = new ArrayList<>();
        workers = new ArrayList<>();
        dealers = new ArrayList<>();
        other = new ArrayList<>();
        storageList = new HashMap<>();
        int numThreads = ConfigParser.parser("/home/artemiy/java-labs/java-lab/lab4/src/main/resources/config.txt", this);
        executorService = Executors.newFixedThreadPool(numThreads + 1);
        Worker.setModel(this);
        for(List<String> list : suppliers){
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
                        FactoryThread object = (FactoryThread) constructor.newInstance(10, storageList.get(list.get(2)));
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

    }

    private void notifyMovement(){
        if(null != listener){
            listener.onModelChanged();
        }
    }

    public long getTimeout(){
        return this.timeout;
    }
    public void addOther(List<String> other) {
        this.other.add(other);
    }
    public void addDealers(List<String> dealers) {
        this.dealers.add(dealers);
    }
    public void addWorkers(List<String> workers) {
        this.workers.add(workers);
    }
    public void addSuppliers(List<String> suppliers) {
        this.suppliers.add(suppliers);
    }
    public void addStorages(List<String> storages) {
        this.listStoragesForParse.add(storages);
    }
    public void setListener(ModelListener listener) {
        this.listener = listener;
    }
    public Map<String, Storage> getStorageList() {
        return storageList;
    }
}
