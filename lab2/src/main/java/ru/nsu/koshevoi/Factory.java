package ru.nsu.koshevoi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Factory {
    public Command newCommand(String[] string){
        try {
            InputStream inputStream = Factory.class.getResourceAsStream("/config");
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while((line = bufferedReader.readLine()) != null){
                    if(line.contains(string[0])){
                        try {
                            String className = line.split(" ")[1];
                            Object command = Class.forName(className).newInstance();
                            return (Command) command;
                        }
                        catch(ClassNotFoundException | InstantiationException | IllegalAccessException e){
                            throw new RuntimeException(e);
                        }
                    }
                }
                //исключение, нет такой команды
            }
            else{
                //исключение
            }
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        return null;
    }
}
