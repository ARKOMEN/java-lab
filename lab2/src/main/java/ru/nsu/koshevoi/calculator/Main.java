package ru.nsu.koshevoi.calculator;

import ru.nsu.koshevoi.exception.CalculatorException;
import ru.nsu.koshevoi.factory.CacheFactory;
import ru.nsu.koshevoi.factory.Command;
import ru.nsu.koshevoi.factory.Factory;
import ru.nsu.koshevoi.factory.FactoryInterface;

import java.util.List;

import static ru.nsu.koshevoi.calculator.Parser.parser;

public class Main {

    public static void main(String[] args) {
        List<List<String>> commands;/*
        if(args == null || args.length == 0){
            commands = parser();
        }
        else{
            commands = parser(args[0]);
        }*/
        commands = parser("/home/artemiy/java-labs/java-lab/lab2/src/main/java/ru/nsu/koshevoi/file.txt");
        Data data = new Data();
        CacheFactory factory = new CacheFactory();
        for(List<String> comm : commands){
            try {
                Command command = factory.createCommand(comm);
                command.command(data, comm);
            }catch (CalculatorException e){
                e.printStackTrace();
            }
        }
    }
}