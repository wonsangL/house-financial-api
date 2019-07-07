package com.homework.kakao.housefinancial.common.util;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import java.util.ArrayList;
import java.util.List;

public class CsvLineTokenizer extends DelimitedLineTokenizer {
    @Override
    protected List<String> doTokenize(String line) {
        StringBuilder lineBuilder = new StringBuilder(line);
        List<String> result = new ArrayList<>();
        boolean flag = false; // ""으로 감싸져 있으면 true
        int cursor = 0; //token의 시작점

        for(int i = 0; i < lineBuilder.length(); i++){
            if(lineBuilder.charAt(i) == '"'){
                flag = !flag;
                lineBuilder.deleteCharAt(i--);
                continue;
            }

            if(lineBuilder.charAt(i) == ','){
                if(flag){
                    //""으로 감싸져 있을 경우는 , 도 무시
                    lineBuilder.deleteCharAt(i--);
                }else{
                    String token = lineBuilder.substring(cursor, i);

                    cursor = i + 1;
                    if(!token.isEmpty()){
                        result.add(token);
                    }
                }
            }
        }

        return result;
    }
}
