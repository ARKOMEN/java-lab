package ru.nsu.koshevoi.calculator;

import ru.nsu.koshevoi.factory.Command;
import ru.nsu.koshevoi.factory.Factory;

import java.util.List;

public class Calculator {
    Data data;
    public Calculator(){
        data = new Data();
    }
    public void calculation(List<List<String>> str){
        Factory factory = new Factory();
        for(List<String> comm : str){
            try {
                Command command = factory.newCommand(comm);
                command.command(data, comm);
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
    }
}
