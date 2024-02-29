package ru.nsu.koshevoi.factory;

import ru.nsu.koshevoi.exception.CalculatorException;
import ru.nsu.koshevoi.exception.CommandNotExist;

import java.io.*;
import java.util.List;

public class SimpleFactory implements Factory {
    public Command createCommand(List<String> string)throws CalculatorException {
        try (InputStream inputStream = SimpleFactory.class.getResourceAsStream("/config")) {
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    List<String> list = List.of(line.split(" "));
                    if (list.getFirst().equals(string.getFirst())) {
                        try {
                            Object command = Class.forName(list.get(1)).newInstance();
                            return (Command) command;
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                throw new CommandNotExist();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
