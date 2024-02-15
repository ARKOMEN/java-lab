package ru.nsu.koshevoi.exception;

public class CommandNotExist extends Exception {
    public CommandNotExist(String message){
        super(message);
    }
}