package com.homework.kakao.housefinancial.model.entity;

import com.homework.kakao.housefinancial.common.InstituteType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "institute")
@NoArgsConstructor
@Getter
@Setter
public class Institute extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long instituteSeq;

    @Enumerated(EnumType.STRING)
    private InstituteType instituteType;

    public Institute(InstituteType type){
        instituteType = type;
    }
}
