package com.homework.kakao.housefinancial;

import com.homework.kakao.housefinancial.common.InstituteType;
import com.homework.kakao.housefinancial.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class APIController {
    @Autowired
    private APIService apiService;

    @GetMapping(value = "institutes")
    public List<String> getInstitutes(){
        return InstituteType.getInstituteNames();
    }

    @PostMapping
    public void updateDatabase() throws Exception{
        apiService.csvToDatabase();
    }

    @GetMapping(value = "annual")
    public AnnualDataDto getAnnualDetail(){
        return apiService.getAnnualDetail();
    }

    @GetMapping(value = "max")
    public MaxAmountDto getMaxAmount() {
        return apiService.getMaxAmount();
    }

    @GetMapping(value = "avg")
    public AvgAmountDto getAvgAmount(){
        return apiService.getAvgMinMax("외환은행");
    }

    @GetMapping(value = "predict")
    public PredictionDto getPrediction(@RequestBody BankAndMonthDto input){
        return apiService.predictAmount(input);
    }
}
