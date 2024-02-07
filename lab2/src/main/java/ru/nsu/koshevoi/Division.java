package ru.nsu.koshevoi;

public class Division implements Command{
    @Override
    public void command(Data data, String[] strings){
        if(data.stack.isEmpty()){
            //исключение, стек пуст
        }
        else if(data.stack.size() == 1){
            //исключение, в стеке один элемент
        }
        else{
            double a = data.stack.pop();
            double b = data.stack.pop();
            if(b == 0){
                //исключение, деление на ноль
            }
            data.stack.push(a/b);
        }
    }
}
