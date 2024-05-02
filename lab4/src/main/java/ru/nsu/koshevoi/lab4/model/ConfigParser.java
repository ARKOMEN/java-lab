package ru.nsu.koshevoi.lab4.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigParser {

    public static int parser(String arg, Model model){
        int num = 0;
        StringBuilder str = new StringBuilder();
        try(BufferedReader buff = new BufferedReader(new FileReader((arg)))){
            String string;
            while ((string = buff.readLine()) != null){
                switch (string){
                    case "#supplier" -> {
                        List<String> list = new ArrayList<>();
                        for(int i = 0; i < 3; i++){
                            list.add(buff.readLine());
                        }
                        num += Integer.parseInt(list.get(1));
                        model.setInputSupplier(list);
                    }
                    case "#worker" -> {
                        List<String> list = new ArrayList<>();
                        for(int i = 0; i < 6; i++){
                            list.add(buff.readLine());
                        }
                        num += Integer.parseInt(list.get(1));
                        model.setInputWorker(list);
                    }
                    case "#dealer" -> {
                        List<String> list = new ArrayList<>();
                        for(int i = 0; i < 3; i++){
                            list.add(buff.readLine());
                        }
                        num += Integer.parseInt(list.get(1));
                        model.setInputDealer(list);
                    }
                    case "#storage" -> {
                        List<String> list = new ArrayList<>();
                        for(int i = 0; i < 4; i++){
                            list.add(buff.readLine());
                        }
                        if(Boolean.parseBoolean(list.get(3))){
                            num++;
                            list.add(buff.readLine());
                        }
                        model.setInputStorage(list);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return num;
    }
}
