package org.task.service;

import org.junit.Test;
import org.task.exception.NominalAmountException;

import java.math.BigInteger;


public class NumberToStringConverterServiceShouldThrowExceptionTest {

    @Test(expected = NominalAmountException.class)
    public void convertNumberToStringShouldThrowNominalAmountException() throws NominalAmountException {
        BigInteger largeNumber = new BigInteger("99999999999999999999999999999999999999999999999999999999999999999999");
        NumberToStringConverterService numberToStringConverterService = new NumberToStringConverterService();
        numberToStringConverterService.convertNumberToString(largeNumber);
    }
}