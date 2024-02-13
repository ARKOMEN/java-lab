package ru.nsu.koshevoi;

import java.util.List;

import static ru.nsu.koshevoi.Parser.parser;

public class Main {

    public static void main(String[] args) {
        List<String[]> commands;
        commands = parser("/home/artemiy/java-labs/java-lab/lab2/src/main/java/ru/nsu/koshevoi/file.txt");/*
        if(args.length == 0){
            commands = parser();
        }
        else{
            commands = parser(args[0]);
        }*/
        Calculator calculator = new Calculator();
        calculator.calculation(commands);
    }
}