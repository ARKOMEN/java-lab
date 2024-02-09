package ru.nsu.koshevoi;

public class СommandNotExist extends CalculatorExceptions{
    public void getException(){
        System.err.println("the command does not exist");
    }
}