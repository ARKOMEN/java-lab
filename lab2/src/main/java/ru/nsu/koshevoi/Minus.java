package ru.nsu.koshevoi;

public class Minus implements Command{
    @Override
    public void command(Data data, String[] strings)throws EmptyStack, InsufficientData{
        if(data.stack.isEmpty()){
            throw new EmptyStack();
        } else if (data.stack.size() == 1) {
            throw new InsufficientData();
        }
        else{
            data.stack.push(data.stack.pop() - data.stack.pop());
        }
    }
}