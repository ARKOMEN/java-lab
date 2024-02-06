package ru.nsu.koshevoi;

import java.io.*;
import java.util.List;

import static ru.nsu.koshevoi.Main.parser;

public class Factory {
    public Command newCommand(String[] string){
        List<String[]> commands = parser("config.txt");
        for(String[] comm : commands){
            if(string[0].equals(comm[0])){
                try{
                    Object command = Class.forName(comm[1]).newInstance();
                    return (Command) command;
                }
                catch(ClassNotFoundException | InstantiationException | IllegalAccessException e){
                    throw new RuntimeException(e);
                }
            }
        }
        //поиск нужного инструмента
        //после загрзка нужного класса и
        //создание объекта этого класса
        //следующим образом
        //Class.forName(<назавание инструмента(класса)>).newInstance()
        return null;
    }
}
