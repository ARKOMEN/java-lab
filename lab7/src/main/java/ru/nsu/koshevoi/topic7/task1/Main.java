package ru.nsu.koshevoi.topic7.task1;

import java.util.Scanner;

class Producer<T> extends Thread{
    private final Storage<T> storage;
    private final int id;
    private int produced = 0;

    public Producer(Storage<T> storage, int id){
        this.storage = storage;
        this.id = id;
    }

    public void run(){
        while (!isInterrupted()) {
            String item = "p" + id + "-" + produced;
            try {
                storage.put((T) item);
            } catch (InterruptedException e) {}
            produced++;
            System.out.println("Producer " + id + " produced " + item);
        }
    }

    public int getProduced(){
        return produced;
    }

    public int getid(){
        return id;
    }
}

class Consumer<T> extends Thread{
    private final Storage<T> storage;
    private final int id;
    private int consumed = 0;
    public Consumer(Storage<T> storage, int id){
        this.storage = storage;
        this.id = id;
    }

    public void run(){
        while(!isInterrupted()){
            String item = null;
            try {
                item = storage.get().toString();
                consumed++;
                System.out.println("Consumer " + id + " consumed " + item);
            } catch (InterruptedException e) { }
            try {
                Thread.sleep((long) (Math.random() * 100) + 50);
            } catch (InterruptedException e) { }
        }
    }

    public int getConsumed(){
        return consumed;
    }

    public int getid(){
        return id;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int N,P,C,T;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите размер склада");
        N = scanner.nextInt();
        System.out.println("Ввеите количество производителей");
        P = scanner.nextInt();
        System.out.println("Введите количество потребителей");
        C = scanner.nextInt();
        System.out.println("Введите продолжительность работы программы");
        T = scanner.nextInt();

        Storage<String> storage = new Storage<>(N);
        Thread[] producers = new Thread[P];
        Thread[] consumers = new Thread[C];

        for(int i = 0; i < P; i++){
            producers[i] = new Thread(new Producer<>(storage, i));
            producers[i].start();

        }
        for(int i = 0; i < C; i++){
            consumers[i] = new Thread(new Consumer<>(storage, i));
            consumers[i].start();
        }

        Thread.sleep(T* 1000L);
        for(Thread producer : producers){
            producer.interrupt();
        }
        System.out.println("производители завершены");

        while (storage.size() > 0){
            Thread.sleep(100);
        }

        System.out.println("склад пуст");

        for(Thread consumer : consumers){
            consumer.interrupt();
        }

        int producedRes = 0, consumedRes = 0;
        for(Thread producer : producers){
            Producer<String> p = (Producer<String>) producer;
            System.out.println("Производитель " + p.getid() + " произвел " + p.getProduced());
            producedRes += p.getProduced();
        }
        for(Thread consumer : consumers){
            Consumer<String> c = (Consumer<String>) consumer;
            System.out.println("Потребитель " + c.getid() + " потребил " + c.getConsumed());
            consumedRes += c.getConsumed();
        }
        System.out.println("Всего произведено" + producedRes);
        System.out.println("Всего потреблено" + consumedRes);
        System.out.println("Через склад прошло " + storage.getNum() + " товаров");
    }
}