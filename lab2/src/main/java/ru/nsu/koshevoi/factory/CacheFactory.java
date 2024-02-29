package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.exception.CalculatorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheFactory implements Factory {
    Factory factory;
    Map<String, Command> map;
    public CacheFactory(Factory newFactory){
        factory = newFactory;
        map = new HashMap<>();
    }
    public Command createCommand(List<String> string)throws CalculatorException{
        if(map.containsKey(string.getFirst())){
            return map.get(string.getFirst());
        }

        map.put(string.getFirst(), factory.createCommand(string));
        return map.get(string.getFirst());
    }
}
