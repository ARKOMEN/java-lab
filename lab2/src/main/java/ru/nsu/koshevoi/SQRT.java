package ru.nsu.koshevoi;

public class SQRT implements Command{
    @Override
    public void command(Data data, String[] strings){
        if(data.stack.isEmpty()){
            //исключение, стек пуст
        }
        else{
            data.stack.push(Math.sqrt(data.stack.pop()));
        }
    }
}
