package org.tuxdevelop.spring.batch.lightmin.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tuxdevelop.spring.batch.lightmin.client.api.controller.JobConfigurationRestController;
import org.tuxdevelop.spring.batch.lightmin.client.api.controller.JobLauncherRestController;
import org.tuxdevelop.spring.batch.lightmin.client.api.controller.JobRestController;
import org.tuxdevelop.spring.batch.lightmin.exception.SpringBatchLightminConfigurationException;
import org.tuxdevelop.spring.batch.lightmin.service.AdminService;
import org.tuxdevelop.spring.batch.lightmin.service.JobExecutionQueryService;
import org.tuxdevelop.spring.batch.lightmin.service.JobService;
import org.tuxdevelop.spring.batch.lightmin.service.StepService;
import org.tuxdevelop.spring.batch.lightmin.support.ControllerServiceEntryBean;
import org.tuxdevelop.spring.batch.lightmin.support.JobLauncherBean;
import org.tuxdevelop.spring.batch.lightmin.support.ServiceEntry;

import javax.sql.DataSource;

/**
 * @author Marcel Becker
 * @version 0.1
 */
@Slf4j
@Configuration
@EnableBatchProcessing
public class SpringBatchLightminConfiguration {

    private ApplicationContext applicationContext;
    private SpringBatchLightminConfigurationProperties springBatchLightminConfigurationProperties;

    @Autowired
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setSpringBatchLightminConfigurationProperties(final SpringBatchLightminConfigurationProperties springBatchLightminConfigurationProperties) {
        this.springBatchLightminConfigurationProperties = springBatchLightminConfigurationProperties;
    }

    @Bean
    @ConditionalOnMissingBean(BatchConfigurer.class)
    public BatchConfigurer batchConfigurer() {
        final DefaultSpringBatchLightminBatchConfigurer batchConfigurer;
        final BatchRepositoryType batchRepositoryType = this.springBatchLightminConfigurationProperties.getBatchRepositoryType();
        switch (batchRepositoryType) {
            case JDBC:
                final DataSource dataSource = this.applicationContext.getBean(
                        this.springBatchLightminConfigurationProperties.getBatchDataSourceName(), DataSource.class);
                final String tablePrefix = this.springBatchLightminConfigurationProperties.getRepositoryTablePrefix();
                batchConfigurer = new DefaultSpringBatchLightminBatchConfigurer(dataSource, tablePrefix);
                break;
            case MAP:
                batchConfigurer = new DefaultSpringBatchLightminBatchConfigurer();
                break;
            default:
                throw new SpringBatchLightminConfigurationException("Unknown BatchRepositoryType: " + batchRepositoryType);

        }
        return batchConfigurer;
    }

    @Bean
    @ConditionalOnMissingBean(SpringBatchLightminConfigurator.class)
    public SpringBatchLightminConfigurator defaultSpringBatchLightminConfigurator(final BatchConfigurer batchConfigurer) {
        final DefaultSpringBatchLightminConfigurator configuration =
                new DefaultSpringBatchLightminConfigurator(this.springBatchLightminConfigurationProperties, this.applicationContext);
        configuration.setBatchConfigurer(batchConfigurer);
        return configuration;
    }

    @Bean
    public ServiceEntry serviceEntry(final AdminService adminService,
                                     final JobService jobService,
                                     final StepService stepService,
                                     final JobExecutionQueryService jobExecutionQueryService,
                                     final JobLauncherBean jobLauncherBean) {
        return new ControllerServiceEntryBean(adminService, jobService, stepService, jobExecutionQueryService, jobLauncherBean);
    }

    @Bean
    public JobConfigurationRestController jobConfigurationRestController(final ServiceEntry serviceEntry,
                                                                         final JobRegistry jobRegistry) {
        return new JobConfigurationRestController(serviceEntry, jobRegistry);
    }

    @Bean
    public JobRestController jobRestController(final ServiceEntry serviceEntry) {
        return new JobRestController(serviceEntry);
    }

    @Bean
    public JobLauncherRestController jobLauncherRestController(final ServiceEntry serviceEntry) {
        return new JobLauncherRestController(serviceEntry);
    }

    @Bean
    public JobLauncherBean jobLauncherBean(final JobLauncher defaultAsyncJobLauncher,
                                           final JobRegistry jobRegistry,
                                           final SpringBatchLightminConfigurationProperties springBatchLightminConfigurationProperties) {
        return new JobLauncherBean(defaultAsyncJobLauncher, jobRegistry, springBatchLightminConfigurationProperties);
    }

}
