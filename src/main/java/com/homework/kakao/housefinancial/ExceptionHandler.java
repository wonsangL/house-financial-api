package com.homework.kakao.housefinancial;

import com.homework.kakao.housefinancial.error.InvalidBankException;
import com.homework.kakao.housefinancial.model.dto.AvgAmountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidBankException.class)
    public ResponseEntity<String> bankNotFound(InvalidBankException e){
        log.error("BankNotFound: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeExceptionHandling(RuntimeException e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
