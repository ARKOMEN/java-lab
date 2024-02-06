package ru.nsu.koshevoi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private String[] parser(String arg){
        StringBuilder str = new StringBuilder();
        try(BufferedReader buff = new BufferedReader(new FileReader((arg)))){
            String command;
            while ((command = buff.readLine()) != null){
                str.append(command).append(" ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] commands = str.toString().split(" ");
        return commands;
    }

    private String[] parser(){
        Scanner sc = new Scanner(System.in);
        StringBuilder str = new StringBuilder();
        while (sc.hasNext()){
            str.append(sc.nextLine()).append(" ");
        }
        String[] commands = str.toString().split(" ");
        return commands;
    }

    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            //стандартный поток ввода
        }
        else{
            try(BufferedReader buff = new BufferedReader(new FileReader((args[0])))){
                String command;
                while ((command = buff.readLine()) != null){
                    String[] words = command.split(" ");
                    Factory commandFactory = new Factory();

                }
            }
        }
    }
}