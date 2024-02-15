package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.InvalidData;

import java.util.List;

public class Push implements Command {
    @Override
    public void command(Data data, List<String> strings)throws Exception{
        if(strings.size() < 2){
            return;
        }
        try{
            double tmp =  Double.parseDouble(strings.get(1));
            data.getStack().push(tmp);
        }catch (NumberFormatException e){
            if(data.getMap().containsKey(strings.get(1))) {
                data.getStack().push(data.getMap().get(strings.get(1)));
            }
            else {
                throw new InvalidData("invalid data");
            }
        }
    }
}
