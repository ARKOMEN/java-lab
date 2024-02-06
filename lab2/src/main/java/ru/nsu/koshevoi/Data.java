package ru.nsu.koshevoi;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Data {
    public Stack<Double> stack;
    public Map<String, Double> map;
    Data(){
        stack = new Stack<>();
        map = new HashMap<>();
    }
}
