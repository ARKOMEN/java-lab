package ru.nsu.koshevoi.exception;

public class ExtractingRootFromNegativeNumber extends CalculatorException {
    public ExtractingRootFromNegativeNumber(){
        super("extracting the root from a negative number");
    }
}
