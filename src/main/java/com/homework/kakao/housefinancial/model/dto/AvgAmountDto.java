package com.homework.kakao.housefinancial.model.dto;

import com.homework.kakao.housefinancial.model.entity.AnnualAmount;
import com.homework.kakao.housefinancial.model.pojo.YearAndAmount;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AvgAmountDto {
    private String bank;
    private List<YearAndAmount> supportMinAmount;
    private List<YearAndAmount> supportMaxAmount;

    public static AvgAmountDto of(String bank, List<AnnualAmount> annualAmounts){
        AvgAmountDto result = new AvgAmountDto();

        result.bank = bank;
        result.setMinMaxAmount(annualAmounts);

        return result;
    }

    private void setMinMaxAmount(List<AnnualAmount> annualAmounts){
        supportMaxAmount = new ArrayList<>();
        supportMinAmount = new ArrayList<>();

        long minAmount = -1L, maxAmount = -1L;

        for(AnnualAmount annualAmount : annualAmounts){
            if(maxAmount == -1){
                minAmount = maxAmount = annualAmount.getAmountAvg();
                continue;
            }

            if(annualAmount.getAmountAvg() > maxAmount){
                maxAmount = annualAmount.getAmountAvg();
            }

            if(annualAmount.getAmountAvg() < minAmount){
                minAmount = annualAmount.getAmountAvg();
            }
        }

        for(AnnualAmount annualAmount : annualAmounts){
            if(annualAmount.getAmountAvg() == minAmount){
                supportMinAmount.add(new YearAndAmount(annualAmount.getYear(), annualAmount.getAmountAvg()));
            }

            if(annualAmount.getAmountAvg() == maxAmount){
                supportMaxAmount.add(new YearAndAmount(annualAmount.getYear(), annualAmount.getAmountAvg()));
            }
        }
    }
}
