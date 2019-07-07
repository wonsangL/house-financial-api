package com.homework.kakao.housefinancial.model.dto;

import com.homework.kakao.housefinancial.model.entity.AnnualAmount;
import com.homework.kakao.housefinancial.model.pojo.YearAndAmount;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AvgAmountDto {
    private String bank;
    private List<YearAndAmount> supportMinAmount;
    private List<YearAndAmount> supportMaxAmount;

    public static AvgAmountDto of(String bank, List<AnnualAmount> min, List<AnnualAmount> max){
        AvgAmountDto result = new AvgAmountDto();

        result.bank = bank;
        result.supportMinAmount = min.stream().map(AnnualAmount::toYearAmount).collect(Collectors.toList());
        result.supportMaxAmount = max.stream().map(AnnualAmount::toYearAmount).collect(Collectors.toList());

        return result;
    }
}
