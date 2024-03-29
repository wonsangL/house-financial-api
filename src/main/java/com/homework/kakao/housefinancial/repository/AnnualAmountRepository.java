package com.homework.kakao.housefinancial.repository;

import com.homework.kakao.housefinancial.model.entity.AnnualAmount;
import com.homework.kakao.housefinancial.model.entity.Institute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnualAmountRepository extends CrudRepository<AnnualAmount, Long> {
    AnnualAmount findByInstituteAndYear(Institute institute, int year);

    List<AnnualAmount> findAllByInstitute(Institute institute);

    @Query(value = "SELECT year FROM annual_amount GROUP BY year", nativeQuery = true)
    List<Integer> findAllYears();

    List<AnnualAmount> findAllByYear(int year);

    @Query(value = "SELECT * FROM annual_amount WHERE total_amount = (SELECT MAX(total_amount) FROM annual_amount)"
            , nativeQuery = true)
    List<AnnualAmount> findAllByMaxAmount();

    @Query(value = "SELECT * FROM annual_amount " +
            "WHERE amount_avg = (SELECT MAX(amount_avg) FROM annual_amount WHERE institute_seq = ?1) " +
            "AND institute_seq = ?1"
            , nativeQuery = true)
    List<AnnualAmount> findAllbyMaxAvgAmountAndInstitute(long instituteSeq);

    @Query(value = "SELECT * FROM annual_amount " +
            "WHERE amount_avg = (SELECT MIN(amount_avg) FROM annual_amount WHERE institute_seq = ?1) " +
            "AND institute_seq = ?1"
            , nativeQuery = true)
    List<AnnualAmount> findAllbyMinAvgAmountAndInstitute(long instituteSeq);
}
