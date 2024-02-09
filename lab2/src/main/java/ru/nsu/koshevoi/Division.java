package ru.nsu.koshevoi;

public class Division implements Command{
    @Override
    public void command(Data data, String[] strings)throws EmptyStack, InsufficientData, DivisionByZero{
        if(data.stack.isEmpty()){
            throw new EmptyStack();
        }
        else if(data.stack.size() == 1){
            throw new InsufficientData();
        }
        else{
            double a = data.stack.pop();
            double b = data.stack.pop();
            if(b == 0){
                data.stack.push(b);
                data.stack.push(a);
                throw new DivisionByZero();
            }
            data.stack.push(a/b);
        }
    }
}
