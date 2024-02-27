package ru.nsu.koshevoi.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Data {
    private Stack<Double> stack;
    private Map<String, Double> map;
    public Data(){
        stack = new Stack<>();
        map = new HashMap<>();
    }
    public Map<String, Double> getMap() {
        return map;
    }
    public Stack<Double> getStack() {
        return stack;
    }
}
