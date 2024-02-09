package ru.nsu.koshevoi;

public class Pop implements Command{
    @Override
    public void command(Data data, String[] strings)throws EmptyStack{
        if(!data.stack.isEmpty()) {
            data.stack.pop();
        }
        else{
            throw new EmptyStack();
        }
    }
}
