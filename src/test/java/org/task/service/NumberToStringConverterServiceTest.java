package org.task.service;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;


public class NumberToStringConverterServiceTest {

    private static final String DATA_FILE_PATH = "src/test/java/resources/testData.txt";


    @Test
    public void numberToStringConverterTest() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE_PATH))) {
            String line;
            String[] splittedString;
            while ((line = br.readLine()) != null) {
                splittedString = line.split(":");
                assertEquals(splittedString[1], numberToString(splittedString[0]));
            }
        }
    }

    private static String numberToString(String input){
        BigInteger number = new BigInteger(input);
        NumberToStringConverterService numberToStringConverterService = new NumberToStringConverterService();
        return numberToStringConverterService.convertNumberToString(number);
    }
}