package ru.nsu.koshevoi.calculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
    public static List<List<String>> parser(String arg){
        StringBuilder str = new StringBuilder();
        try(BufferedReader buff = new BufferedReader(new FileReader((arg)))){
            String command;
            while ((command = buff.readLine()) != null){
                str.append(command).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> tmp = new ArrayList<String>(List.of(str.toString().split("\n")));
        List<List<String>> list = new ArrayList<>();
        for(String string: tmp){
            list.add(List.of(string.split(" ")));
        }
        return list;
    }

    public static List<List<String>> parser(){
        Scanner sc = new Scanner(System.in);
        StringBuilder str = new StringBuilder();
        while (sc.hasNext()){
            str.append(sc.nextLine());
        }
        List<String> tmp = new ArrayList<String>(List.of(str.toString().split("\n")));
        List<List<String>> list = new ArrayList<>();
        for(String string: tmp){
            list.add(List.of(string.split(" ")));
        }
        return list;
    }
}
