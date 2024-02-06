package ru.nsu.koshevoi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<String[]> parser(String arg){
        StringBuilder str = new StringBuilder();
        InputStream i = Main.class.getResourceAsStream(arg);
        try(BufferedReader buff = new BufferedReader(new InputStreamReader((i)))){
            String command;
            while ((command = buff.readLine()) != null){
                str.append(command);
            }
            i.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] tmp = str.toString().split("\n");
        List<String[]> list = new ArrayList<>();
        for(String string: tmp){
            list.add(string.split(" "));
        }
        return list;
    }

    public static List<String[]> parser(){
        Scanner sc = new Scanner(System.in);
        StringBuilder str = new StringBuilder();
        while (sc.hasNext()){
            str.append(sc.nextLine());
        }
        String[] tmp = str.toString().split("\n");
        List<String[]> list = new ArrayList<>();
        for(String string: tmp){
            list.add(string.split(" "));
        }
        return list;
    }

    public static void main(String[] args) {
        List<String[]> commands;
        if(args.length == 0){
            commands = parser();
        }
        else{
            commands = parser(args[0]);
        }
        Calculator calculator = new Calculator();
        calculator.calculation(commands);
    }
}