package org.task.exception;

/**
 * Thrown to indicate that there is no enough nominal names available
 * @author Denis Khamitsevich
 */
public class NominalAmountException extends RuntimeException{
    public NominalAmountException(String message){
        super(message);
    }
}
