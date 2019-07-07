package com.homework.kakao.housefinancial.repository;

import com.homework.kakao.housefinancial.common.InstituteType;
import com.homework.kakao.housefinancial.model.entity.Institute;
import org.springframework.data.repository.CrudRepository;

public interface InstituteRepository extends CrudRepository<Institute, Long> {
    Institute findByInstituteType(InstituteType instituteType);
}


