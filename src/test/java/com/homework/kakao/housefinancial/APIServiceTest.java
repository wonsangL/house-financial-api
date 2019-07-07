package com.homework.kakao.housefinancial;

import static org.mockito.Mockito.*;

import com.homework.kakao.housefinancial.common.InstituteType;
import com.homework.kakao.housefinancial.error.InvalidBankException;
import com.homework.kakao.housefinancial.model.entity.AnnualAmount;
import com.homework.kakao.housefinancial.model.entity.Institute;
import com.homework.kakao.housefinancial.repository.AnnualAmountRepository;
import com.homework.kakao.housefinancial.repository.InstituteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class APIServiceTest {
    @InjectMocks
    private APIService apiService;

    @Mock
    private AnnualAmountRepository annualAmountRepo;

    @Mock
    private InstituteRepository instituteRepo;


    @Test
    public void getMaxAmount() {
        Institute institute = new Institute();
        institute.setInstituteType(InstituteType.KOOKMIN);

        AnnualAmount annualAmount = new AnnualAmount();

        annualAmount.setYear(2019);
        annualAmount.setInstitute(institute);

        List<AnnualAmount> annualAmounts = Collections.singletonList( annualAmount );
        when(annualAmountRepo.findAllByMaxAmount()).thenReturn(annualAmounts);

        Assert.assertEquals(institute.getInstituteType().getInstituteName(),
                apiService.getMaxAmount().getMaxAmount().get(0).getBank());

        Assert.assertEquals(annualAmount.getYear(), apiService.getMaxAmount().getMaxAmount().get(0).getYear());
    }

    @Test
    public void getAvgMinMax() {
        AnnualAmount max = new AnnualAmount();
        max.setYear(2019);
        max.setAmountAvg(1000);

        AnnualAmount min = new AnnualAmount();
        min.setYear(2019);
        min.setAmountAvg(0);

        List<AnnualAmount> annualAmounts = Arrays.asList(max, min);
        when(annualAmountRepo.findAllByInstitute(any())).thenReturn(annualAmounts);

        Assert.assertEquals( max.getAmountAvg(),
                apiService.getAvgMinMax("국민은행").getSupportMaxAmount().get(0).getAmount());
        Assert.assertEquals( max.getYear(),
                apiService.getAvgMinMax("국민은행").getSupportMaxAmount().get(0).getYear());

        Assert.assertEquals( min.getAmountAvg(),
                apiService.getAvgMinMax("국민은행").getSupportMinAmount().get(0).getAmount());
        Assert.assertEquals( min.getYear(),
                apiService.getAvgMinMax("국민은행").getSupportMinAmount().get(0).getYear());

    }

    @Test
    public void getAnnualDetail() {
        Institute institute = new Institute();
        institute.setInstituteType(InstituteType.KOOKMIN);

        AnnualAmount annualAmount = new AnnualAmount();
        annualAmount.setTotalAmount(1000);
        annualAmount.setInstitute(institute);

        List<AnnualAmount> annualAmounts = Collections.singletonList(annualAmount);

        when(annualAmountRepo.findAllYears()).thenReturn(Collections.singletonList(2019));
        when(annualAmountRepo.findAllByYear(anyInt())).thenReturn(annualAmounts);

        Assert.assertTrue(apiService.getAnnualDetail()
                .getAnnualDetails()
                .get(0)
                .getDetailAmount()
                .containsKey(institute.getInstituteType().getInstituteName()));

        Assert.assertEquals(annualAmount.getTotalAmount(),
                apiService.getAnnualDetail().getAnnualDetails().get(0).getTotalAmount());
    }

    @Test
    public void findInstituteByName() {
        Institute institute = new Institute();
        when(instituteRepo.findByInstituteType(any())).thenReturn(institute);
        Assert.assertEquals(institute, apiService.findInstituteByName("국민은행"));
    }

    @Test(expected = InvalidBankException.class)
    public void findInstituteByWrongName(){
        apiService.findInstituteByName("카카오 뱅크");
    }
}