package com.homework.kakao.housefinancial.common.util;

import com.homework.kakao.housefinancial.model.pojo.CsvRecord;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashMap;

public class CsvItemProcessor implements ItemProcessor<CsvRecord, HashMap<String, Long>> {
    @Override
    public HashMap<String, Long> process(CsvRecord item) throws Exception {
        return null;
    }
}
