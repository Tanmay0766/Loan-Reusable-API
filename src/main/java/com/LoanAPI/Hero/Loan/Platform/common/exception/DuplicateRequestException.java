package com.LoanAPI.Hero.Loan.Platform.common.exception;

public class DuplicateRequestException extends RuntimeException {

    public DuplicateRequestException(String message) {
        super(message);
    }
}