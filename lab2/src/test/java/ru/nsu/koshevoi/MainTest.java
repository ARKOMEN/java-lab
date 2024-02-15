package ru.nsu.koshevoi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nsu.koshevoi.calculator.Main;

import java.io.*;

/*/home/artemiy/java-labs/java-lab/lab2/src/main/java/ru/nsu/koshevoi/file.txt*/

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
    public void sum() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename[0]))){
            writer.write(
                    "DEFINE a 4\n" +
                    "PUSH a\n" +
                    "SQRT\n" +
                    "PRINT"
            );
        }
        Main.main(filename);
        Assert.assertEquals("2.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void division1() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "PUSH 4\n"+"PUSH 2\n" + "/\n" + "PRINT\n"
            );
        }
        Main.main(filename);

        Assert.assertEquals("2.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void division2() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "PUSH 4\n" + "/\n" + "PRINT\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("4.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void division3() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "PUSH 4\n"+"PUSH 0\n" + "/\n" + "PRINT\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("0.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void division4() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "PUSH 0\n"+"PUSH 4\n" + "/\n" + "PRINT\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("0.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void division5() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "/\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("", outputStreamCaptor.toString().trim());
    }
    @Test
    public void division6() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "5/3\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("", outputStreamCaptor.toString().trim());
    }
    @Test
    public void division7() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "PUSH 5/3\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("", outputStreamCaptor.toString().trim());
    }
    @Test
    public void division8() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "PUSH 1\n" + "PUSH 3\n" + "/\n" + "PRINT\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("0.3333333333333333", outputStreamCaptor.toString().trim());
    }
    @Test
    public void PUSH1() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "PUSH\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("", outputStreamCaptor.toString().trim());
    }
    @Test
    public void PUSH2() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "PUSH aaaa\n" + "PRINT\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("", outputStreamCaptor.toString().trim());
    }
    @Test
    public void PUSH3() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "PUSH 1\n" + "PRINT\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("1.0", outputStreamCaptor.toString().trim());
    }
    @Test
    public void PUSH4() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "PUSH a 1\n" + "PRINT\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("", outputStreamCaptor.toString().trim());
    }
    @Test
    public void POP() throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename[0])))) {
            writer.write(
                    "\n"
            );
        }
        Main.main(filename);
        Assert.assertEquals("", outputStreamCaptor.toString().trim());
    }
}
