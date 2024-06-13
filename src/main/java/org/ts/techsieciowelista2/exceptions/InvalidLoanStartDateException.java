package org.ts.techsieciowelista2.exceptions;

public class InvalidLoanStartDateException extends RuntimeException {
    public InvalidLoanStartDateException(String message) {
        super(message);
    }
}
