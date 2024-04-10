package ru.nsu.koshevoi.topic7.task1;

import java.util.Scanner;

class Info{
    private int num = 0;
    private int id;
    public void plus(){
        num++;
    }
    public int getNum(){
        return num;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
}

class Producer<T> extends Somebody<T>{


    public Producer(Storage<T> storage, int id, Info info){
        super(storage, id, info);
    }
    @Override
    public void run(){
        try{
            while(true){
                String item = "p" + id + "-" + num;
                storage.put((T)item);
                num++;
                info.plus();
                System.out.println("Producer " + id + " produced " + item);
            }
        }
        catch (InterruptedException e){}
    }
}

class Consumer<T> extends Somebody<T> {
    public Consumer(Storage<T> storage, int id, Info info){
        super(storage, id, info);
    }
    @Override
    public void run(){
        try{
            while(true){
                String item = storage.get().toString();
                num++;
                info.plus();
                System.out.println("Consumer " + id + " consumed " + item);
            }
        }
        catch (InterruptedException e){}
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
        Info[] infoProducers = new Info[P];
        Info[] infoConsumers = new Info[C];

        for(int i = 0; i < P; i++){
            infoProducers[i] = new Info();
            producers[i] = new Thread(new Producer<>(storage, i, infoProducers[i]));
            producers[i].start();

        }
        for(int i = 0; i < C; i++){
            infoConsumers[i] = new Info();
            consumers[i] = new Thread(new Consumer<>(storage, i, infoConsumers[i]));
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
        for(Info info : infoProducers){
            System.out.println("Producer " + info.getId() + " produced " + info.getNum() + "items");
            producedRes += info.getNum();
        }
        for(Info info : infoConsumers){
            System.out.println("Consumer " + info.getId() + " consumed " + info.getNum() + "items");
            consumedRes += info.getNum();
        }
        System.out.println("Всего произведено" + producedRes);
        System.out.println("Всего потреблено" + consumedRes);
        System.out.println("Через склад прошло " + storage.getNum() + " товаров");
    }
}