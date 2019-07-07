package com.homework.kakao.housefinancial.model.pojo;

import com.homework.kakao.housefinancial.model.entity.AnnualAmount;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Setter
@Getter
public class AnnualDetail {
    private String year;
    private long totalAmount;
    private HashMap<String, Long> detailAmount;

    public AnnualDetail(int year){
        this.year = year + "년";
        detailAmount = new HashMap<>();
    }

    public static AnnualDetail of(int year, List<AnnualAmount> annualAmounts){
        AnnualDetail detail = new AnnualDetail(year);

        annualAmounts.forEach(annualAmount -> {
            detail.totalAmount += annualAmount.getTotalAmount();
            detail.detailAmount.put(annualAmount.getInstituteName(), annualAmount.getTotalAmount());
        });

        return detail;
    }
}
