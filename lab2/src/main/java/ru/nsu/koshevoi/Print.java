package ru.nsu.koshevoi;

public class Print implements Command{
    @Override
    public void command(Data data, String[] strings){
        if(data.stack.isEmpty()){
            //стек пуст
        }
        else{
            double tmp = data.stack.pop();
            System.out.println(tmp);
            data.stack.push(tmp);
        }
    }
}
