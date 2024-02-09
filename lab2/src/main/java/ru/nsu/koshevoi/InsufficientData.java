package ru.nsu.koshevoi;

public class InsufficientData extends CalculatorExceptions {
    public void getException(){
        System.err.println("there are not enough elements in the stack to perform the operation");
    }
}
