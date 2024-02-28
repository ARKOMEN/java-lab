package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.exception.CalculatorException;

import java.util.List;

public interface Factory {
    public Command createCommand(List<String> string)throws CalculatorException;
}
