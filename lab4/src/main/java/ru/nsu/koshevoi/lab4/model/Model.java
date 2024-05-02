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
    private Map<Integer, Storage> storageMap;
    private ControllerCar controllerCar;
    private ModelListener listener;
    private ExecutorService executorService;
    private double engineTimeout = 10;
    private double bodyTimeout = 10;
    private double accessoryTimeout = 10;
    private double carTimeout = 10;
    private List<FactoryThread> listOfThreads;
    private List<FactoryThread> listOfDealers;

    public void setInputSupplier(List<String> inputSupplier) {
        this.inputSupplier.add(inputSupplier);
    }

    private List<List<String>> inputSupplier;

    public void setInputWorker(List<String> inputWorker) {
        this.inputWorker.add(inputWorker);
    }

    private List<List<String>> inputWorker;

    public void setInputStorage(List<String> inputStorage) {
        this.inputStorage.add(inputStorage);
    }

    private List<List<String>> inputStorage;

    public void setInputDealer(List<String> inputDealer) {
        this.inputDealer.add(inputDealer);
    }

    private List<List<String>> inputDealer;

    public boolean isFlagForWorkers() {
        return flagForWorkers;
    }

    public void setFlagForWorkers(boolean flagForWorkers) {
        this.flagForWorkers = flagForWorkers;
    }

    private boolean flagForWorkers;

    public Model(ModelListener listener) {

        inputSupplier = new ArrayList<>();
        inputDealer = new ArrayList<>();
        inputWorker = new ArrayList<>();
        inputStorage = new ArrayList<>();
        storageMap = new HashMap<>();

        listOfDealers = new ArrayList<>();
        listOfThreads = new ArrayList<>();
        flagForWorkers = true;
        this.listener = listener;
        thread = new Ticker(this);
        thread.start();

        int numThreads = ConfigParser.parser("/home/artemiy/java-labs/java-lab/lab4/src/main/resources/config.txt", this);
        executorService = Executors.newFixedThreadPool(numThreads);
        for (List<String> list : inputStorage) {
            try {
                Constructor<?> constructor = Class.forName(list.get(1)).getDeclaredConstructor(int.class);
                Storage storage = (Storage) constructor.newInstance(Integer.parseInt(list.get(2)));
                if (Boolean.parseBoolean(list.get(3))) {
                    Constructor<?> constructorController = Class.forName(list.get(4)).getDeclaredConstructor(Storage.class);
                    Controller controller = (Controller) constructorController.newInstance(storage);
                    storage.setController(controller);
                }
                storageMap.put(Integer.parseInt(list.getFirst()), storage);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        for (List<String> list : inputWorker) {
            try {
                for (int i = 0; i < Integer.parseInt(list.get(1)); i++) {
                    List<Storage> input = new ArrayList<>();
                    for (int j = 3; j < list.size(); j++) {
                        input.add(storageMap.get(Integer.parseInt(list.get(j))));
                    }
                    Class<?> clazz = Class.forName(list.getFirst());
                    Constructor<?> constructor = clazz.getDeclaredConstructor(Storage.class, List.class);
                    FactoryThread object = (FactoryThread) constructor.newInstance(storageMap.get(Integer.parseInt(list.get(2))), input);
                    listOfThreads.add(object);
                    executorService.submit(object);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        for (List<String> list : inputSupplier) {
            try {
                for (int i = 0; i < Integer.parseInt(list.get(1)); i++) {
                    Class<?> clazz = Class.forName(list.getFirst());
                    Constructor<?> constructor = clazz.getDeclaredConstructor(Storage.class);
                    FactoryThread object = (FactoryThread) constructor.newInstance(storageMap.get(Integer.parseInt(list.get(2))));
                    listOfThreads.add(object);
                    executorService.submit(object);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        for (List<String> list : inputDealer) {
            try {
                for (int i = 0; i < Integer.parseInt(list.get(1)); i++) {
                    Class<?> clazz = Class.forName(list.getFirst());
                    Constructor<?> constructor = clazz.getDeclaredConstructor(Storage.class);
                    FactoryThread object = (FactoryThread) constructor.newInstance(storageMap.get(Integer.parseInt(list.get(2))));
                    listOfThreads.add(object);
                    listOfDealers.add(object);
                    executorService.submit(object);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
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

    public Map<Integer, Storage> getStorageList() {
        return storageMap;
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
