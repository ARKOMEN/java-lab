package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;

import java.util.List;

public interface Command{
    void command(Data data, List<String> strings) throws Exception;
}
