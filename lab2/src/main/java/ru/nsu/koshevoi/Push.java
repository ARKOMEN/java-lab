package ru.nsu.koshevoi;

public class Push implements Command{
    @Override
    public void command(Data data, String[] strings){
        try{
            double tmp =  Double.parseDouble(strings[1]);
            data.stack.push(tmp);
        }catch (NumberFormatException e){
            data.stack.push(data.map.get(strings[1]));
        }
    }
}
