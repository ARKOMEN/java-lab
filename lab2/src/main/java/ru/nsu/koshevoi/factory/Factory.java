package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.exception.CommandNotExist;

import java.io.*;
import java.util.List;

public class Factory {
    public Command newCommand(List<String> string)throws Exception{
        try (InputStream inputStream = Factory.class.getResourceAsStream("/config")) {
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(string.getFirst())) {
                        try {
                            String className = line.split(" ")[1];
                            Object command = Class.forName(className).newInstance();
                            return (Command) command;
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                throw new CommandNotExist("the command does not exist");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
