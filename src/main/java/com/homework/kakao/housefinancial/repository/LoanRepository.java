package com.homework.kakao.housefinancial.repository;

import com.homework.kakao.housefinancial.model.entity.Loan;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, Long> {
}
