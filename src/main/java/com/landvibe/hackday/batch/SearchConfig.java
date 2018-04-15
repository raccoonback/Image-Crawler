package com.landvibe.hackday.batch;

import com.landvibe.hackday.domain.Search;
import com.landvibe.hackday.sql.SearchSQL;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static com.landvibe.hackday.batch.JdbcReader.jdbcReader;

@Configuration
public class SearchConfig {

    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final String JOB_NAME = "SearchJob";
    private static final String STEP_NAME = "SearchStep";

    @Autowired
    public SearchConfig(@Qualifier("getDataSource") DataSource dataSource, PlatformTransactionManager transactionManager, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.dataSource = dataSource;
        this.transactionManager = transactionManager;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job searchJob(Step step) throws Exception {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step searchStep() {

        return stepBuilderFactory.get(STEP_NAME)
                .<Search, Search>chunk(10)
                .reader(searchReader())
                .processor(searchProcessor())
                .writer(searchWriter())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public ItemReader<Search> searchReader() {
        return jdbcReader(Search.class, dataSource, SearchSQL.SELECT_SEARCH);
    }

    @Bean(initMethod = "init", destroyMethod = "tearDown")
    public SearchProcessor searchProcessor() {
        return new SearchProcessor();
    }

    @Bean
    public ItemWriter<Search> searchWriter() {
        return new JdbcBatchItemWriterBuilder<Search>()
                .dataSource(dataSource)
                .beanMapped()
                .sql(SearchSQL.UPDATE_SEARCH)
                .build();
    }

}
