package ru.nsu.koshevoi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Factory {
    public Command newCommand(String[] string){
        try {
            InputStream inputStream = Class.class.getResourceAsStream("/home/artemiy/java-labs/java-lab/lab2/src/main/java/ru/nsu/koshevoi/config");
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while((line = bufferedReader.readLine()) != null){
                    if(line.contains(string[0])){
                        try {
                            Object command = Class.forName(line.split(" ")[1]).newInstance();
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
