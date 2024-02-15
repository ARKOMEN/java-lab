package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.CalculatorException;

import java.util.List;

public interface Command{
    void command(Data data, List<String> strings) throws CalculatorException;
}
