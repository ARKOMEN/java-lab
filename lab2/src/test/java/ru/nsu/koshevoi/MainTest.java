package ru.nsu.koshevoi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
    public void mainTest() throws IOException {
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
}
