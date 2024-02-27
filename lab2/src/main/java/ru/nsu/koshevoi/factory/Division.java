package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.CalculatorException;
import ru.nsu.koshevoi.exception.DivisionByZero;
import ru.nsu.koshevoi.exception.InsufficientData;

import java.util.List;

public class Division implements Command {
    @Override
    public void command(Data data, List<String> strings)throws CalculatorException {
        if(data.getStack().size() < 2){
            throw new InsufficientData();
        }
        else{
            double a = data.getStack().pop();
            double b = data.getStack().pop();
            if(a == 0){
                data.getStack().push(b);
                data.getStack().push(a);
                throw new DivisionByZero();
            }
            data.getStack().push(b/a);
        }
    }
}