package com.homework.kakao.housefinancial.model.dto;

import com.homework.kakao.housefinancial.model.pojo.AnnualDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class AnnualDataDto {
    private String name;
    private List<AnnualDetail> annualDetails;

    public AnnualDataDto(String name){
        this.name = name;
        annualDetails = new ArrayList<>();
    }

    public void add(AnnualDetail annualDetail){
        annualDetails.add(annualDetail);
    }
}
