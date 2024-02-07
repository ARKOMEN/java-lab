package ru.nsu.koshevoi;

public class Push implements Command{
    @Override
    public void command(Data data, String[] strings){
        if(Double.isFinite(Double.parseDouble(strings[1]))){
            data.stack.push(Double.parseDouble(strings[1]));
        }
        else if(data.map.containsKey(strings[1])){
            data.stack.push(data.map.get(strings[1]));
        }
        else{
            //какая-та ашибка
        }
    }
}
