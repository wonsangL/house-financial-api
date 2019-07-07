package com.homework.kakao.housefinancial.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "loan")
@NoArgsConstructor
@Getter
public class Loan extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long loanSeq;

    @Column(nullable = false)
    private long amount;

    @Column(nullable = false)
    private int month;

    public Loan(int month, long amount){
        this.amount = amount;
        this.month = month;
    }
}
