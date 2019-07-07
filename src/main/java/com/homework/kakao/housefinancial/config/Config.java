package com.homework.kakao.housefinancial.config;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Slf4j
@EnableJpaAuditing
@Configuration
public class Config {
    @Value("${resource.csv}")
    private String CSV_FILE;

    @Bean
    public CSVReader csvReader(){
        CSVReader csvReader = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE));
            csvReader = new CSVReader(reader);
        }catch (FileNotFoundException e){
            log.error(e.getMessage());
        }

        return csvReader;
    }
}
