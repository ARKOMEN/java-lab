package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.CalculatorException;
import ru.nsu.koshevoi.exception.EmptyStack;

import java.util.List;

public class Print implements Command {
    @Override
    public void command(Data data, List<String> strings)throws CalculatorException {
        if(data.getStack().isEmpty()){
            throw new EmptyStack("the stack is empty");
        }
        else{
            double tmp = data.getStack().pop();
            System.out.println(tmp);
            data.getStack().push(tmp);
        }
    }
}
