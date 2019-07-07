package com.homework.kakao.housefinancial.config;

import com.homework.kakao.housefinancial.common.InstituteType;
import com.homework.kakao.housefinancial.common.util.CsvFieldSetMapper;
import com.homework.kakao.housefinancial.common.util.CsvLineTokenizer;
import com.homework.kakao.housefinancial.model.entity.AnnualAmount;
import com.homework.kakao.housefinancial.model.entity.Institute;
import com.homework.kakao.housefinancial.model.entity.Loan;
import com.homework.kakao.housefinancial.model.pojo.CsvRecord;
import com.homework.kakao.housefinancial.repository.AnnualAmountRepository;
import com.homework.kakao.housefinancial.repository.InstituteRepository;
import com.homework.kakao.housefinancial.repository.LoanRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


@EnableBatchProcessing
@Configuration
public class BatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private InstituteRepository instituteRepo;

    @Autowired
    private AnnualAmountRepository annualAmountRepo;

    @Autowired
    private LoanRepository loanRepo;


    @Bean
    public Job csvToDatabaseJob(){
        return jobBuilderFactory.get("csvToDatabaseJob")
                .start(csvToDatabaseStep())
                .build();
    }

    @Bean
    public Step csvToDatabaseStep(){
        return stepBuilderFactory.get("csvToDatabaseStep")
                .chunk(1)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public FlatFileItemReader<CsvRecord> reader(){
        FlatFileItemReader<CsvRecord> reader = new FlatFileItemReader<>();

        reader.setResource(new ClassPathResource("input.csv"));
        reader.setLinesToSkip(1);

        CsvLineTokenizer tokenizer = new CsvLineTokenizer();
        tokenizer.setNames("year",
                "month",
                "cityfund",
                "bank00",
                "bank01",
                "bank02",
                "bank03",
                "bank04",
                "bank05",
                "bank06",
                "etc");

        CsvFieldSetMapper fieldSetMapper = new CsvFieldSetMapper();

        DefaultLineMapper<CsvRecord> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public ItemWriter<Object> writer(){
        return items -> {
            for(Object item : items){
                CsvRecord record = (CsvRecord)item;

                int year = record.getYear();
                int month = record.getMonth();

                record.getAmount().keySet().iterator().forEachRemaining(code -> {
                    InstituteType type = InstituteType.findByCode(code);

                    Institute institute = instituteRepo.findByInstituteType(type);

                    if(institute == null){
                        institute = instituteRepo.save(new Institute(type));
                    }

                    AnnualAmount annualAmount = annualAmountRepo.findByInstituteAndYear(institute, year);

                    if(annualAmount == null){
                        annualAmount = annualAmountRepo.save(new AnnualAmount(institute, year));
                    }

                    annualAmount.add(loanRepo.save(new Loan(month, record.getAmount().get(code))));
                });
            }
        };
    }
}
