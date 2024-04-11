package ru.nsu.koshevoi.topic10.task1;

import com.google.common.graph.Traverser;
import com.google.common.io.Files;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Traverser<File> traverser = Files.fileTraverser();
    }
}