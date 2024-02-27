package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.CalculatorException;
import ru.nsu.koshevoi.exception.InsufficientData;

import java.util.List;

public class Print implements Command {
    @Override
    public void command(Data data, List<String> strings)throws CalculatorException {
        if(data.getStack().isEmpty()){
            throw new InsufficientData();
        }
        else{
            double tmp = data.getStack().pop();
            System.out.println(tmp);
            data.getStack().push(tmp);
        }
    }
}
