package ru.nsu.koshevoi.topic11.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args){
        if(args.length < 2){
            System.err.println("error");
        }
        String str;
        try(
                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[1]));
                Socket socket = serverSocket.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ){
            while((str = in.readLine()) != null){

            }
        }catch (IOException e){
            System.err.println("AHHHH");
        }
    }
}