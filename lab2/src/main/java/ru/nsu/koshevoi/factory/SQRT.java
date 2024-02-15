package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.CalculatorException;
import ru.nsu.koshevoi.exception.EmptyStack;
import ru.nsu.koshevoi.exception.ExtractingRootFromNegativeNumber;

import java.util.List;

public class SQRT implements Command {
    @Override
    public void command(Data data, List<String> strings)throws CalculatorException {
        if(data.getStack().isEmpty()){
            throw new EmptyStack("the stack is empty");
        }
        else{
            double tmp = data.getStack().pop();
            if(tmp < 0){
                data.getStack().push(tmp);
                throw new ExtractingRootFromNegativeNumber("extracting the root from a negative number");
            }
            else {
                data.getStack().push(Math.sqrt(tmp));
            }
        }
    }
}
