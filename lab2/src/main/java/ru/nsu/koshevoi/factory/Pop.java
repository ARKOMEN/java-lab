package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.CalculatorException;
import ru.nsu.koshevoi.exception.EmptyStack;

import java.util.List;

public class Pop implements Command {
    @Override
    public void command(Data data, List<String> strings)throws CalculatorException {
        if(!data.getStack().isEmpty()) {
            data.getStack().pop();
        }
        else{
            throw new EmptyStack("the stack is empty");
        }
    }
}
