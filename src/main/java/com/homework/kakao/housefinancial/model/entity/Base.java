package com.homework.kakao.housefinancial.model.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Base {
    @CreatedDate
    @Column(nullable = false)
    LocalDateTime createdDatetime;

    @LastModifiedDate
    @Column(nullable = false)
    LocalDateTime modifiedDatetime;
}
