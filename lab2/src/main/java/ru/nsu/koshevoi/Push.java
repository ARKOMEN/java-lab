package ru.nsu.koshevoi;

public class Push implements Command{
    @Override
    public void command(Data data, String[] strings)throws InvalidData{
        if(strings.length < 2){
            return;
        }
        try{
            double tmp =  Double.parseDouble(strings[1]);
            data.stack.push(tmp);
        }catch (NumberFormatException e){
            if(data.map.containsKey(strings[1])) {
                data.stack.push(data.map.get(strings[1]));
            }
            else {
                throw new InvalidData();
            }
        }
    }
}
