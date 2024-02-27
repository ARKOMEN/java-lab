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
        return getLists(str);
    }
    public static List<List<String>> parser(){
        Scanner sc = new Scanner(System.in);
        StringBuilder str = new StringBuilder();
        String buf;
        while (sc.hasNext()){
            buf = sc.nextLine();
            if(buf.equals("exit")){
                break;
            }
            str.append(buf).append("\n");
        }
        return getLists(str);
    }
    private static List<List<String>> getLists(StringBuilder str) {
        List<String> tmp = new ArrayList<String>(List.of(str.toString().split("\n")));
        List<List<String>> list = new ArrayList<>();
        for(String string: tmp){
            list.add(List.of(string.split(" ")));
        }
        return list;
    }
}
