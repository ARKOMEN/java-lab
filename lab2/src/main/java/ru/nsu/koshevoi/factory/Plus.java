package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.EmptyStack;
import ru.nsu.koshevoi.exception.InsufficientData;

import java.util.List;

public class Plus implements Command {
    @Override
    public void command(Data data, List<String> strings) throws Exception{
        if(data.getStack().isEmpty()){
            throw new EmptyStack("the stack is empty");
        }
        else if(data.getStack().size() == 1){
            throw new InsufficientData("there are not enough elements in the stack to perform the operation");
        }
        else{
            data.getStack().push(data.getStack().pop() + data.getStack().pop());
        }
    }
}
