package ru.nsu.koshevoi.exception;

public class CommandNotExist extends CalculatorException {
    public CommandNotExist(){
        super("the command does not exist");
    }
}