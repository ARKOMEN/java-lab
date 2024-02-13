package ru.nsu.koshevoi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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

    @Test
    public void mainTest(){
        Main.main(null);
        System.out.print("DEFINE a 4\n");
        System.out.print("PUSH a\n");
        System.out.print("SQRT\n");
        System.out.print("PRINT\n");
        Assert.assertEquals("2.0", outputStreamCaptor.toString().trim());
    }
}
