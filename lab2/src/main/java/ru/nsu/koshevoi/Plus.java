package ru.nsu.koshevoi;

import java.util.Stack;

public class Plus implements Command{
    @Override
    public void command(Data data, String[] strings) {
        if(data.stack.isEmpty()){
            //исключение, стек пуст
        }
        else if(data.stack.size() == 1){
            //исключение, в стеке один элемент
        }
        else{
            data.stack.push(data.stack.pop() + data.stack.pop());
        }
    }
}
