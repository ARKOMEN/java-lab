package ru.nsu.koshevoi.lab4.model;

import ru.nsu.koshevoi.lab4.model.dealers.Dealer;
import ru.nsu.koshevoi.lab4.model.storages.and.warehouses.Storage;
import ru.nsu.koshevoi.lab4.model.suppliers.Supplier;
import ru.nsu.koshevoi.lab4.model.workers.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {
    private long timeout = 200;
    private Thread thread;
    private List<List<String>> storages;
    private List<List<String>> suppliers;
    private List<List<String>> workers;
    private List<List<String>> dealers;
    private List<List<String>> other;
    public static void main(String[] args) {
        new Model();
    }
    public Model() {
        thread = new Ticker(this);
        thread.start();
        storages = new ArrayList<>();
        suppliers = new ArrayList<>();
        workers = new ArrayList<>();
        dealers = new ArrayList<>();
        other = new ArrayList<>();
        int numThreads = ConfigParser.parser("/home/artemiy/java-labs/java-lab/lab4/src/main/resources/config.txt", this);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        for(List<String> list : storages){
            try{
               Storage object = (Storage) Class.forName(list.getFirst()).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        for(List<String> list: suppliers){
            try{
                for(int i = 0; i < Integer.parseInt(list.get(1)); i++){
                    Supplier object = (Supplier) Class.forName(list.getFirst()).newInstance();
                    executorService.execute(object);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        for(List<String> list: workers){
            try{
                for(int i = 0; i < Integer.parseInt(list.get(1)); i++){
                    Worker object = (Worker) Class.forName(list.getFirst()).newInstance();
                    executorService.execute(object);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        for(List<String> list: dealers){
            try{
                for(int i = 0; i < Integer.parseInt(list.get(1)); i++){
                    Dealer object = (Dealer) Class.forName(list.getFirst()).newInstance();
                    executorService.execute(object);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void update(){

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
        this.storages.add(storages);
    }
}
