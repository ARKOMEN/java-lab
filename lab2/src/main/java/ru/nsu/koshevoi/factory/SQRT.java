package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.CalculatorException;
import ru.nsu.koshevoi.exception.ExtractingRootFromNegativeNumber;
import ru.nsu.koshevoi.exception.InsufficientData;

import java.util.List;

public class SQRT implements Command {
    @Override
    public void command(Data data, List<String> strings)throws CalculatorException {
        if(data.getStack().isEmpty()){
            throw new InsufficientData();
        }
        else{
            double tmp = data.getStack().pop();
            if(tmp < 0){
                data.getStack().push(tmp);
                throw new ExtractingRootFromNegativeNumber();
            }
            else {
                data.getStack().push(Math.sqrt(tmp));
            }
        }
    }
}
