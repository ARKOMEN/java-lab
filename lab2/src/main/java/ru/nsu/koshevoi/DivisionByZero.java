package ru.nsu.koshevoi;

public class DivisionByZero extends CalculatorExceptions{
    public void getException(){
        System.err.println("division by zero");
    }
}