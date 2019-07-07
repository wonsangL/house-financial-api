package com.homework.kakao.housefinancial;

import com.homework.kakao.housefinancial.error.InvalidBankException;
import com.homework.kakao.housefinancial.model.dto.AvgAmountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidBankException.class)
    public AvgAmountDto bankNotFound(InvalidBankException e){
        log.error("BankNotFound: " + e.getMessage());
        return null;
    }
}
