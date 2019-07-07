package com.homework.kakao.housefinancial.model.dto;

import com.homework.kakao.housefinancial.model.entity.AnnualAmount;
import com.homework.kakao.housefinancial.model.pojo.YearAndBank;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MaxAmountDto {
    private List<YearAndBank> maxAmount;

    public static MaxAmountDto of(List<AnnualAmount> annualAmounts){
        MaxAmountDto result = new MaxAmountDto();

        result.maxAmount = annualAmounts.stream().map(AnnualAmount::toYearBank).collect(Collectors.toList());
        return result;
    }
}
