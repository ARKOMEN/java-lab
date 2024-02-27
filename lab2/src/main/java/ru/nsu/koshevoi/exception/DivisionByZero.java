package ru.nsu.koshevoi.exception;

public class DivisionByZero extends CalculatorException {
    public DivisionByZero() {
        super("division by zero");
    }
}