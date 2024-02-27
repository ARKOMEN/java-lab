package ru.nsu.koshevoi.exception;

public class InsufficientData extends CalculatorException {
    public InsufficientData(){
        super("there are not enough elements in the stack to perform the operation");
    }
}
