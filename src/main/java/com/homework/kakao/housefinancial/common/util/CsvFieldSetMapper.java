package com.homework.kakao.housefinancial.common.util;

import com.homework.kakao.housefinancial.model.pojo.CsvRecord;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class CsvFieldSetMapper extends BeanWrapperFieldSetMapper {
    @Override
    public CsvRecord mapFieldSet(FieldSet fs) {
        CsvRecord record = new CsvRecord();
        String[] names = fs.getNames();
        String[] values = fs.getValues();

        record.setYear(Integer.parseInt(values[0]));
        record.setMonth(Integer.parseInt(values[1]));

        for(int i = 2; i < fs.getFieldCount(); i++){
            record.addAmount(names[i], Long.parseLong(values[i]));
        }
        return record;
    }
}
