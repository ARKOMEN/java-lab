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
                if(string.contains("Storage") || string.contains("Warehouse")){
                    model.addStorages(List.of(string.split("=")));
                }
                else if(string.contains("Supplier")){
                    num += Integer.parseInt(List.of(string.split("=")).get(1));
                    model.addSuppliers(List.of(string.split("=")));
                }
                else if(string.contains("Worker")){
                    num += Integer.parseInt(List.of(string.split("=")).get(1));
                    model.addWorkers(List.of(string.split("=")));
                }
                else if(string.contains("Dealer")){
                    num += Integer.parseInt(List.of(string.split("=")).get(1));
                    model.addDealers(List.of(string.split("=")));
                }
                else if(string.contains("LogSale")){
                    model.addOther(List.of(string.split("=")));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return num;
    }
}
