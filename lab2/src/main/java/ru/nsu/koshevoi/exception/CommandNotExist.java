package ru.nsu.koshevoi.exception;

public class CommandNotExist extends CalculatorException {
    public CommandNotExist(String message){
        super(message);
    }
}