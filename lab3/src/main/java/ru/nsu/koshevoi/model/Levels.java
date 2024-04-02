package ru.nsu.koshevoi.model;

import java.util.ArrayList;
import java.util.List;

public class Levels {
    public List<Long> getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time.add(time);
    }

    private List<Long> time;
    public void setValue(int value) {
        this.value = value;
    }

    private int value;
    Levels(int value) {
        this.value = value;
        this.time = new ArrayList<>();
    }
    public int getValue() {
        return this.value;
    }
}
