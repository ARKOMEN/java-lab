package ru.nsu.koshevoi;

public class Print implements Command{
    @Override
    public void command(Data data, String[] strings)throws EmptyStack{
        if(data.stack.isEmpty()){
            throw new EmptyStack();
        }
        else{
            double tmp = data.stack.pop();
            System.out.println(tmp);
            data.stack.push(tmp);
        }
    }
}
