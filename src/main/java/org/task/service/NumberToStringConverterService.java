package org.task.service;

import org.task.exception.NominalAmountException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.task.constants.NumberConstants.*;

/**
 * Class that converts BigInteger number into string of words
 *
 * @author Denis Khamitsevich
 */

public class NumberToStringConverterService {
    /**
     * Available russian nominal names
     */
    private final String[][] nominalNames;

    /**
     * Path for a file with nominal names
     */
    private static final String NOMINAL_NAMES_FILE_PATH = "src/main/resources/nominalNames.txt";

    /**
     * Constant for masculine gender in nominal names
     */
    private static final String MASCULINE_GENDER = "м";


    public NumberToStringConverterService() {
        this.nominalNames = readNominalNamesFromFile();
    }

    /**
     * Converts given number into string of words
     * @param number  number to be converted
     * @return String that is verbal representation of a given number
     */
    public String convertNumberToString(BigInteger number) {
        if (number.equals(BigInteger.ZERO)) {
            return ZERO;
        }

        String result = "";
        if (number.signum() == -1) {
            result = MINUS + " ";
            number = number.abs();
        }

        List<Integer> separatedNumber = separateNumberByPowerOfThousand(number);
        result += convertSeparatedNumberToString(separatedNumber);

        return result;
    }

    /**
     * Converts given number into string of words
     * @param separatedNumbers  number to be converted, that is separated by the power of 1000
     * @return String that is verbal representation of a given number
     */
    private String convertSeparatedNumberToString(List<Integer> separatedNumbers) {
        if (separatedNumbers.size() > nominalNames.length) {
            throw new NominalAmountException("Not enough nominals for that number!");
        }
        StringBuilder result = new StringBuilder();
        int powerOfThousand = separatedNumbers.size() - 1;
        String elementString;
        for (int index = separatedNumbers.size() - 1; index >= 0; index--, powerOfThousand--) {
            Integer element = separatedNumbers.get(index);
            if (element == 0) {
                continue;
            }
            elementString = element.toString();
            if (elementString.length() == 2) elementString = "0" + elementString;
            if (elementString.length() == 1) elementString = "00" + elementString;
            int d3 = Integer.parseInt(elementString.substring(2, 3)); //3rd digit of a number
            int d23 = Integer.parseInt(elementString.substring(1, 3)); //2nd and 3rd digit of a number
            int d2 = Integer.parseInt(elementString.substring(1, 2)); //2nd digit of a number
            int d1 = Integer.parseInt(elementString.substring(0, 1)); //1st digit of a number

            result.append(d1 == 0 ? "" : HUNDREDS[d1] + " ");
            if (d23 > 10 && d23 < 20) {
                result.append(TEENS[d3 + 1]).append(" ");
            } else {
                result.append(d2 == 0 ? "" : TENS[d2] + " ");
                int numberCaseForNominal = getNumberCaseForNominal(powerOfThousand);
                result.append(d3 == 0 ? "" : ONES[numberCaseForNominal][d3] + " ");
            }
            int nominalIndexForLastDigit = getNominalIndexForLastDigit(d3);
            result.append(nominalNames[powerOfThousand][nominalIndexForLastDigit]).append(" ");

        }

        return result.toString().trim();
    }

    /**
     * Finds the appropriate nominal form index for a given digit
     * @param digit  digit to be matched with nominal form
     * @return index of the appropriate nominal form
     */
    private int getNominalIndexForLastDigit(int digit) {
        // We have 3 intervals for nominal forms based on the digit: first - 1, second - {2-4}, third - {0,5+}
        if (digit == 1)
            return 0;
        else if (digit >= 2 && digit <= 4)
            return 1;
        return 2;
    }

    /**
     * Finds the appropriate nominal name index for a given power of thousand based on its gender
     * @param powerOfThousand  power of thousand to be matched with nominal name
     * @return index of the appropriate nominal name
     */
    private int getNumberCaseForNominal(int powerOfThousand) {
        return nominalNames[powerOfThousand][3].equals(MASCULINE_GENDER) ? 0 : 1;
    }

    /**
     * Separates number into List of Integer by the power of thousand
     * @param number  number to be separated
     * @return List that contains parts of separated number
     */
    private List<Integer> separateNumberByPowerOfThousand(BigInteger number) {
        List<Integer> result = new ArrayList<>();
        BigInteger thousand = new BigInteger("1000");
        BigInteger[] bigIntegers;
        while (!number.equals(BigInteger.ZERO)) {
            bigIntegers = number.divideAndRemainder(thousand);
            result.add(bigIntegers[1].intValue());
            number = bigIntegers[0];
        }

        return result;
    }

    /**
     * Reads nominal names from a file
     * @return two-dimensional array that contains nominal names and their gender
     */
    private String[][] readNominalNamesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(NOMINAL_NAMES_FILE_PATH))) {
            List<String> nominalNamesList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                nominalNamesList.add(line);
            }
            String[][] nominalNames = new String[nominalNamesList.size() / 4 + 1][4];
            nominalNames[0][0] = "";
            nominalNames[0][1] = "";
            nominalNames[0][2] = "";
            nominalNames[0][3] = MASCULINE_GENDER;
            for (int i = 0; i < nominalNamesList.size() / 4; i++) {
                for (int j = 0; j < 4; j++) {
                    nominalNames[i + 1][j] = nominalNamesList.get(i * 4 + j);
                }
            }

            return nominalNames;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
