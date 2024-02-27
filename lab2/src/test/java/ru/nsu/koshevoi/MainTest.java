package ru.nsu.koshevoi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nsu.koshevoi.calculator.Data;
import ru.nsu.koshevoi.exception.CalculatorException;
import ru.nsu.koshevoi.factory.*;

import java.io.*;
import java.util.List;


/**
 * Error message:
 * the command does not exist
 * division by zero
 * extracting the root from a negative number
 * there are not enough elements in the stack to perform the operation
 * invalid data
 */

public class MainTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @After
    public void tearDown() {
        System.setOut(standardOut);
    }
    String[] filename = {"/home/artemiy/java-labs/java-lab/lab2/src/main/java/ru/nsu/koshevoi/file.txt"};
    @Test
    public void sqrt1(){
        Data data = new Data();
        Command command = new Define();
        command.command(data, List.of("DEFINE", "a", "4"));
        command = new Push();
        command.command(data, List.of("PUSH", "a"));
        command = new SQRT();
        command.command(data, List.of("SQRT"));
        command = new Print();
        command.command(data, List.of("PRINT"));
        Assert.assertEquals("2.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void sqrt2() {
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
            Data data = new Data();
            Command command = new Define();
            command.command(data, List.of("DEFINE", "a", "-4"));
            command = new Push();
            command.command(data, List.of("PUSH", "a"));
            command = new SQRT();
            command.command(data, List.of("SQRT"));
        });
        Assert.assertEquals("extracting the root from a negative number", thrown.getMessage());
    }
    @Test
    public void division1(){
        Data data = new Data();
        Command command = new Push();
        command.command(data,List.of("PUSH", "4"));
        command = new Push();
        command.command(data, List.of("PUSH", "2"));
        command = new Division();
        command.command(data, List.of("/"));
        command = new Print();
        command.command(data, List.of("PRINT"));
        Assert.assertEquals("2.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void division2(){
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
            Data data = new Data();
            Command command = new Push();
            command.command(data, List.of("PUSH", "4"));
            command = new Division();
            command.command(data, List.of("/"));
        });
        Assert.assertEquals("there are not enough elements in the stack to perform the operation", thrown.getMessage());
    }
    @Test
    public void division3(){
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
            Data data = new Data();
            Command command = new Push();
            command.command(data, List.of("PUSH", "4"));
            command = new Push();
            command.command(data, List.of("PUSH", "0"));
            command = new Division();
            command.command(data, List.of("/"));
        });
        Assert.assertEquals("division by zero", thrown.getMessage());
    }
    @Test
    public void division4(){
        Data data = new Data();
        Command command = new Push();
        command.command(data, List.of("PUSH", "0"));
        command = new Push();
        command.command(data, List.of("PUSH", "4"));
        command = new Division();
        command.command(data, List.of("/"));
        command = new Print();
        command.command(data, List.of("PRINT"));
        Assert.assertEquals("0.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void division5(){
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
            Data data = new Data();
            Command command = new Division();
            command.command(data, List.of("/"));
        });
        Assert.assertEquals("there are not enough elements in the stack to perform the operation", thrown.getMessage());
    }
    @Test
    public void division6() {
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
            Factory factory = new Factory();
            Command command = factory.newCommand(List.of("5/3"));
        });
        Assert.assertEquals("the command does not exist", thrown.getMessage());
    }
    @Test
    public void division8() {
        Data data = new Data();
        Command command = new Push();
        command.command(data, List.of("PUSH", "1"));
        command = new Push();
        command.command(data, List.of("PUSH", "3"));
        command = new Division();
        command.command(data, List.of("/"));
        command = new Print();
        command.command(data, List.of("PRINT"));
        Assert.assertEquals("0.3333333333333333", outputStreamCaptor.toString().trim());
    }
    @Test
    public void push1() {
        Data data = new Data();
        Command command = new Push();
        command.command(data, List.of("PUSH"));
        Assert.assertEquals("", outputStreamCaptor.toString().trim());
    }
    @Test
    public void push2(){
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
            Data data = new Data();
            Command command = new Push();
            command.command(data, List.of("PUSH", "a"));
        });
        Assert.assertEquals("invalid data", thrown.getMessage());
    }
    @Test
    public void push3() {
        Data data = new Data();
        Command command  = new Push();
        command.command(data, List.of("PUSH", "1"));
        command = new Print();
        command.command(data, List.of("PRINT"));
        Assert.assertEquals("1.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void push4() {
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
            Data data = new Data();
            Command command = new Push();
            command.command(data, List.of("PUSH", "a", "4"));
        });
        Assert.assertEquals("invalid data", thrown.getMessage());
    }
    @Test
    public void push5() {
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
            Data data = new Data();
            Command command = new Push();
            command.command(data, List.of("PUSH", "5/3"));
        });
        Assert.assertEquals("invalid data", thrown.getMessage());
    }
    @Test
    public void pop(){
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
                    Data data = new Data();
                    Command command = new Pop();
                    command.command(data, List.of("POP"));
        });
        Assert.assertEquals("there are not enough elements in the stack to perform the operation", thrown.getMessage());
    }
    @Test
    public void plus1(){
        Data data = new Data();
        Command command = new Push();
        command.command(data, List.of("PUSH", "1"));
        command.command(data, List.of("PUSH", "1"));
        command = new Plus();
        command.command(data, List.of("+"));
        command = new Print();
        command.command(data, List.of("PRINT"));
        Assert.assertEquals("2.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void plus2(){
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
           Data data = new Data();
           Command command = new Push();
           command.command(data, List.of("PUSH","1"));
           command = new Plus();
           command.command(data, List.of("+"));
        });
        Assert.assertEquals("there are not enough elements in the stack to perform the operation", thrown.getMessage());
    }
    @Test
    public void minus1(){
        Data data = new Data();
        Command command = new Push();
        command.command(data, List.of("PUSH", "1"));
        command.command(data, List.of("PUSH", "1"));
        command = new Minus();
        command.command(data, List.of("-"));
        command = new Print();
        command.command(data, List.of("PRINT"));
        Assert.assertEquals("0.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void minus2(){
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
            Data data = new Data();
            Command command = new Push();
            command.command(data, List.of("PUSH","1"));
            command = new Minus();
            command.command(data, List.of("-"));
        });
        Assert.assertEquals("there are not enough elements in the stack to perform the operation", thrown.getMessage());
    }
    @Test
    public void multiply1(){
        Data data = new Data();
        Command command = new Push();
        command.command(data, List.of("PUSH", "1"));
        command.command(data, List.of("PUSH", "2"));
        command = new Multiply();
        command.command(data, List.of("*"));
        command = new Print();
        command.command(data, List.of("PRINT"));
        Assert.assertEquals("2.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void multiply2(){
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
            Data data = new Data();
            Command command = new Push();
            command.command(data, List.of("PUSH","1"));
            command = new Multiply();
            command.command(data, List.of("*"));
        });
        Assert.assertEquals("there are not enough elements in the stack to perform the operation", thrown.getMessage());
    }
    @Test
    public void print(){
        CalculatorException thrown = Assert.assertThrows(CalculatorException.class, () -> {
            Data data = new Data();
            Command command = new Print();
            command.command(data, List.of("PRINT"));
        });
        Assert.assertEquals("there are not enough elements in the stack to perform the operation", thrown.getMessage());
    }
}
//проверить работает ли ввод с консоли :)