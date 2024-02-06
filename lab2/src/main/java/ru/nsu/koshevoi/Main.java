package ru.nsu.koshevoi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static String[] parser(String arg){
        StringBuilder str = new StringBuilder();
        try(BufferedReader buff = new BufferedReader(new FileReader((arg)))){
            String command;
            while ((command = buff.readLine()) != null){
                str.append(command).append(" ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str.toString().split(" ");
    }

    private static String[] parser(){
        Scanner sc = new Scanner(System.in);
        StringBuilder str = new StringBuilder();
        while (sc.hasNext()){
            str.append(sc.nextLine()).append(" ");
        }
        return str.toString().split(" ");
    }

    public static void main(String[] args) {
        String[] commands;
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