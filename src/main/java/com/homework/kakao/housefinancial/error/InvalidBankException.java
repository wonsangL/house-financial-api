package com.homework.kakao.housefinancial.error;

public class InvalidBankException extends RuntimeException {
    public InvalidBankException(String message) {
        super(message);
    }
}
