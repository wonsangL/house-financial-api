package com.homework.kakao.housefinancial.model.dto;

import com.homework.kakao.housefinancial.model.entity.Loan;
import com.homework.kakao.housefinancial.prediction.LinearRegression;
import lombok.Getter;

import java.util.List;

@Getter
public class PredictionDto {
    private String bank;
    private int year;
    private int month;
    private long amount;

    public PredictionDto(String bank, int month){
        this.bank = bank;
        year = 2018;
        this.month = month;
    }

    public void predictAmount(List<Loan> loans){
        LinearRegression linearRegression = new LinearRegression();

        linearRegression.addDataSet(year, loans);
        amount = linearRegression.execute(year, month);
    }
}
