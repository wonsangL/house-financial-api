package com.homework.kakao.housefinancial.model.entity;

import com.homework.kakao.housefinancial.model.pojo.YearAndAmount;
import com.homework.kakao.housefinancial.model.pojo.YearAndBank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "annual_amount")
@NoArgsConstructor
@Getter
@Setter
public class AnnualAmount extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long annualSeq;

    @ManyToOne
    @JoinColumn(name = "institute_seq")
    private Institute institute;

    @OneToMany
    @JoinColumn(name = "annual_seq")
    private List<Loan> loans;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private long totalAmount;

    @Column(nullable = false)
    private long amountAvg;

    public AnnualAmount(Institute institute, int year){
        this.institute = institute;
        this.year = year;
        loans = new ArrayList<>();
    }

    public void add(Loan loan){
        totalAmount += loan.getAmount();

        loans.add(loan);
        amountAvg = Math.round((double)totalAmount / loans.size());
    }

    public String getInstituteName(){
        return institute.getInstituteType().getInstituteName();
    }

    public YearAndBank toYearBank(){
        return new YearAndBank(year, getInstituteName());
    }

    public YearAndAmount toYearAmount(){
        return new YearAndAmount(year, amountAvg);
    }
}

