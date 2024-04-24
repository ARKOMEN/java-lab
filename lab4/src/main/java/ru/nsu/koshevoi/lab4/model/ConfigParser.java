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
                if(string.contains("supplier")){
                    num += Integer.parseInt(List.of(string.split("=")).get(1));
                    model.addSuppliers(List.of(string.split("=")));
                }
                else if(string.contains("dealer")){
                    num += Integer.parseInt(List.of(string.split("=")).get(1));
                    model.addSuppliers(List.of(string.split("=")));
                }
                else if(string.contains("worker")){
                    num += Integer.parseInt(List.of(string.split("=")).get(1));
                    model.addSuppliers(List.of(string.split("=")));
                }
                else if(string.contains("storage") || string.contains("warehouse")){
                    model.addSuppliers(List.of(string.split("=")));
                }
                else if(string.contains("LogSale")){
                    model.addSuppliers(List.of(string.split("=")));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return num;
    }
}
