package ru.nsu.koshevoi.exception;

public class DivisionByZero extends CalculatorException {
    public DivisionByZero(String message) {
        super(message);
    }
}