package com.homework.kakao.housefinancial;

import com.homework.kakao.housefinancial.common.InstituteType;
import com.homework.kakao.housefinancial.error.InvalidBankException;
import com.homework.kakao.housefinancial.model.dto.*;
import com.homework.kakao.housefinancial.model.pojo.AnnualDetail;
import com.homework.kakao.housefinancial.repository.AnnualAmountRepository;
import com.homework.kakao.housefinancial.repository.InstituteRepository;
import com.homework.kakao.housefinancial.repository.LoanRepository;
import com.homework.kakao.housefinancial.model.entity.AnnualAmount;
import com.homework.kakao.housefinancial.model.entity.Institute;
import com.homework.kakao.housefinancial.model.entity.Loan;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class APIService {
    @Autowired
    private LoanRepository loanRepo;

    @Autowired
    private InstituteRepository instituteRepo;

    @Autowired
    private AnnualAmountRepository annualAmountRepo;

    @Autowired
    private CSVReader csvReader;

    public void csvToDatabase() throws Exception{
        //Header 정보는 생략
        csvReader.skip(1);

        csvReader.readAll().forEach(record -> {
            int year = Integer.parseInt(record[0]);
            int month = Integer.parseInt(record[1]);

            int index = 2;
            
            for(InstituteType type : InstituteType.values()){
                Institute institute = instituteRepo.findByInstituteType(type);

                if(institute == null){
                    institute = instituteRepo.save(new Institute(type));
                }

                AnnualAmount annualAmount = annualAmountRepo.findByInstituteAndYear(institute, year);

                if(annualAmount == null){
                    annualAmount = annualAmountRepo.save(new AnnualAmount(institute, year));
                }

                long amount = Long.parseLong(record[index++].replaceAll(",", ""));

                for(Loan loan : annualAmount.getLoans()){
                    if(loan.getMonth() == month){
                        return;
                    }
                }

                annualAmount.add(loanRepo.save(new Loan(month, amount)));
            }
        });
    }

    @Transactional(readOnly = true)
    public AnnualDataDto getAnnualDetail(){
        AnnualDataDto annualDataDto = new AnnualDataDto("주택금융 공급현황");

        annualAmountRepo.findAllYears().forEach(
                year -> annualDataDto.add(AnnualDetail.of(year, annualAmountRepo.findAllByYear(year))));

        return annualDataDto;
    }

    @Transactional(readOnly = true)
    public MaxAmountDto getMaxAmount(){
        return MaxAmountDto.of(annualAmountRepo.findAllByMaxAmount());
    }

    @Transactional(readOnly = true)
    public AvgAmountDto getAvgMinMax(String bank){
        return AvgAmountDto.of(bank, annualAmountRepo.findAllByInstitute(findInstituteByName(bank)));
    }

    @Transactional(readOnly = true)
    public PredictionDto predictAmount(BankAndMonthDto input){
        Institute institute = findInstituteByName(input.getBank());

        PredictionDto result = new PredictionDto(institute.getInstituteType().getInstituteCode(), input.getMonth());

        List<Loan> loans = new ArrayList<>();
        annualAmountRepo.findAllByInstitute(institute).forEach(annualAmount -> loans.addAll(annualAmount.getLoans()));

        result.predictAmount(loans);

        return result;
    }

    @Transactional(readOnly = true)
    public Institute findInstituteByName(String bank){
        InstituteType type = InstituteType.findByName(bank);

        if(type == null){
            throw new InvalidBankException(bank + ", 해당 은행은 존재하지 않습니다.");
        }

        return instituteRepo.findByInstituteType(type);
    }
}
