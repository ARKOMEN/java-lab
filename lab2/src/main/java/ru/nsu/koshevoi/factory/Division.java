package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.CalculatorException;
import ru.nsu.koshevoi.exception.DivisionByZero;
import ru.nsu.koshevoi.exception.EmptyStack;
import ru.nsu.koshevoi.exception.InsufficientData;

import java.util.List;

public class Division implements Command {
    @Override
    public void command(Data data, List<String> strings)throws CalculatorException {
        if(data.getStack().isEmpty()){
            throw new EmptyStack("the stack is empty");
        }
        else if(data.getStack().size() == 1){
            throw new InsufficientData("there are not enough elements in the stack to perform the operation");
        }
        else{
            double a = data.getStack().pop();
            double b = data.getStack().pop();
            if(a == 0){
                data.getStack().push(b);
                data.getStack().push(a);
                throw new DivisionByZero("division by zero");
            }
            data.getStack().push(b/a);
        }
    }
}
