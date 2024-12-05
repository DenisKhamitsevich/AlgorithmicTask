package org.task;


import org.task.exception.NominalAmountException;
import org.task.service.NumberToStringConverterService;

import java.math.BigInteger;
import java.util.Scanner;

public class ConverterApp {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter number:");
            String input = scanner.next();
            BigInteger inputNumber = new BigInteger(input);
            NumberToStringConverterService numberToStringConverterService = new NumberToStringConverterService();
            System.out.println(numberToStringConverterService.convertNumberToString(inputNumber));
        } catch (NominalAmountException e){
            System.out.println(e.getMessage());
        }
        catch (NumberFormatException e){
            System.out.println("Input is not a number!");
        }
    }
}