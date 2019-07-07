package com.homework.kakao.housefinancial.common;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum InstituteType {
    CITYFUND("주택도시기금", "cityfund"),
    KOOKMIN("국민은행", "bank00"),
    WOORI("우리은행", "bank01"),
    SHINHAN("신한은행", "bank02"),
    HANKOOK("한국시티은행", "bank03"),
    HANA("하나은행", "bank04"),
    NONGHYUP("농협은행/수협은행", "bank05"),
    OEHWAN("외환은행", "bank06"),
    ETC("기타은행", "etc");

    String instituteName;
    String instituteCode;

    InstituteType(String name, String code){
        instituteName = name;
        instituteCode = code;
    }

    public static List<String> getInstituteNames(){
        List<String> result = new ArrayList<>();

        for(InstituteType i : values()){
            result.add(i.instituteName);
        }

        return result;
    }

    public static InstituteType findByName(String name){
        for(InstituteType i : values()){
            if(i.instituteName.equals(name)){
                return i;
            }
        }
        return null;
    }

    public static InstituteType findByCode(String code){
        for(InstituteType i : values()){
            if(i.instituteCode.equals(code)){
                return i;
            }
        }
        return null;
    }
}
