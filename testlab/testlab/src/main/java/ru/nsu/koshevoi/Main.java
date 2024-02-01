package ru.nsu.koshevoi;
import java.io.*;
import java.lang.StringBuilder;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args[0] == null){
            System.err.println("no file or directory.");
        }

        try(BufferedReader buff = new BufferedReader(new FileReader("file.txt"));){
                                
        }

    }
}