package com.homework.kakao.housefinancial.prediction;

import com.homework.kakao.housefinancial.model.entity.Loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LinearRegression {
    private static final double LEARNING_LATE = 1e-10;
    private static final double MAX_ITER = 1e5;
    private static final double ALLOWANCE = 0.1;

    private static final LocalDate MIN_DATE = LocalDate.of(2004, 1, 1);

    private List<Long> dataSetX = new ArrayList<>();
    private List<Long> dataSetY = new ArrayList<>();

    private double theta0 = 0;
    private double theta1 = 0;
    private double iter = 0;

    public void addDataSet(int year, List<Loan> loans){
        loans.forEach(loan -> {
            dataSetX.add(ChronoUnit.DAYS.between(MIN_DATE, LocalDate.of(year, loan.getMonth(), 1)));
            dataSetY.add(loan.getAmount());
        });
    }

    public long execute(int year, int month){
        do{
            double temp0 = theta0 - LEARNING_LATE * deriveTheta0();
            double temp1 = theta1 - LEARNING_LATE * deriveTheta1();

            theta0 = temp0; theta1 = temp1;

            iter++;

            if(iter > MAX_ITER){
                break;
            }
        } while(cost() > ALLOWANCE);

        return Math.round(hypothesis(ChronoUnit.DAYS.between(MIN_DATE, LocalDate.of(year, month, 1))));
    }

    private double cost(){
        double sum = 0;

        for(int i = 0; i < dataSetX.size(); i++){
            sum += Math.pow(hypothesis(dataSetX.get(i)) - dataSetY.get(i), 2);
        }

        return sum / (dataSetX.size() * 2);
    }

    private double hypothesis(long x){
        return theta0 * x + theta1;
    }

    private double deriveTheta0(){
        double sum = 0;

        for(int i = 0; i < dataSetX.size(); i++){
            sum += (hypothesis(dataSetX.get(i)) - dataSetY.get(i)) * dataSetX.get(i);
        }

        return sum / dataSetX.size();
    }

    private double deriveTheta1(){
        double sum = 0;

        for(int i = 0; i < dataSetX.size(); i++){
            sum += (hypothesis(dataSetX.get(i)) - dataSetY.get(i));
        }

        return sum / dataSetX.size();
    }
}
