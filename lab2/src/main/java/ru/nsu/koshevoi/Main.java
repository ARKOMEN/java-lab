package ru.nsu.koshevoi;

import java.util.List;

import static ru.nsu.koshevoi.Parser.parser;

public class Main {

    public static void main(String[] args) {
        List<String[]> commands;
        if(args == null || args.length == 0){
            commands = parser();
        }
        else{
            commands = parser(args[0]);
        }
        Calculator calculator = new Calculator();
        calculator.calculation(commands);
    }
}