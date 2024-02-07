package ru.nsu.koshevoi;

public class Define implements Command{
    @Override
    public void command(Data data, String[] strings){
        data.map.put(strings[1], Double.valueOf(strings[2]));
    }
}
