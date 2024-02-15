package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.CalculatorException;
import ru.nsu.koshevoi.exception.InvalidData;

import java.util.List;

public class Define implements Command {
    @Override
    public void command(Data data, List<String> strings)throws CalculatorException {
        try{
            double tmp =  Double.parseDouble(strings.get(2));
            data.getMap().put(strings.get(1), tmp);
        }catch (NumberFormatException e){
            throw new InvalidData("invalid data");
        }
    }
}
