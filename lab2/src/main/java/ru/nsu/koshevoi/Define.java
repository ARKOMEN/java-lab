package ru.nsu.koshevoi;

public class Define implements Command{
    @Override
    public void command(Data data, String[] strings)throws InvalidData{
        try{
            double tmp =  Double.parseDouble(strings[2]);
            data.map.put(strings[1], tmp);
        }catch (NumberFormatException e){
            throw new InvalidData();
        }
    }
}
