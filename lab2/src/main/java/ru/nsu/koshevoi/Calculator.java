package ru.nsu.koshevoi;

import java.util.List;
import java.util.Stack;

public class Calculator {
    Data data;
    Calculator(){
        data = new Data();
    }
    public void calculation(List<String[]> str){
        Factory factory = new Factory();
        for(String[] comm : str){
            try {
                Command command = factory.newCommand(comm);
                command.command(data, comm);
            }catch (CalculatorExceptions e){
                e.getException();
            }
        }
    }
}