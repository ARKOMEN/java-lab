package ru.nsu.koshevoi;

public class Pop implements Command{
    @Override
    public void command(Data data, String[] strings){
        if(data.stack.isEmpty()) {
            data.stack.pop();
        }
        else{
            //исключение, стек пуст
        }
    }
}
