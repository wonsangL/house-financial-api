package com.homework.kakao.housefinancial.model.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class CsvRecord {
    private int year;
    private int month;

    private HashMap<String, Long> amount = new HashMap<>();

    public void addAmount(String bankCode, long amount){
        this.amount.put(bankCode, amount);
    }
}
