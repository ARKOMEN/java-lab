package ru.nsu.koshevoi.lab4.model;

import javafx.concurrent.Service;
import ru.nsu.koshevoi.lab4.model.dealers.Dealer;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.*;
import ru.nsu.koshevoi.lab4.model.suppliers.AccessorySupplier;
import ru.nsu.koshevoi.lab4.model.suppliers.BodySupplier;
import ru.nsu.koshevoi.lab4.model.suppliers.EngineSupplier;
import ru.nsu.koshevoi.lab4.model.workers.Worker;
import ru.nsu.koshevoi.lab4.model.workers.Workman;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Model {
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
    private int N1;
    private int N2;
    private int N3;
    private int M;
    private List<FactoryThread> listOfThreads;
    private List<Future<?>> futureList;
    public boolean isFlagForWorkers() {
        return flagForWorkers;
    }

    public void setFlagForWorkers(boolean flagForWorkers) {
        this.flagForWorkers = flagForWorkers;
    }

    private boolean flagForWorkers;

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
        listOfThreads = new ArrayList<>();
        futureList = new ArrayList<>();
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
                        FactoryThread object = (FactoryThread) constructor.newInstance(Integer.parseInt(list.get(3)), storageList.get(list.get(2)));
                        futureList.add(executorService.submit(object));
                        listOfThreads.add(object);
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void update(){
        for(FactoryThread object : listOfThreads){
            switch (object.getType()){
                case dealer -> ((Dealer)object).setTimeout(M);
                case bodySupplier -> ((BodySupplier)object).setTimeout(N1);
                case engineSupplier -> ((EngineSupplier)object).setTimeout(N2);
                case accessorySupplier -> ((AccessorySupplier)object).setTimeout(N3);
            }
        }
        notifyMovement();
    }

    public void stop(){
        executorService.shutdownNow();
    }

    private void notifyMovement(){
        if(null != listener){
            listener.onModelChanged();
        }
    }

    public long getTimeout(){
        return 200;
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
    public int getN1() {
        return N1;
    }
    public int getN2() {
        return N2;
    }
    public int getN3() {
        return N3;
    }
    public int getM() {
        return M;
    }
    public void setN1(int n1) {
        N1 = n1;
    }
    public void setN2(int n2) {
        N2 = n2;
    }
    public void setN3(int n3) {
        N3 = n3;
    }
    public void setM(int m) {
        M = m;
    }
    public List<Future<?>> getFutureList() {
        return futureList;
    }
}
