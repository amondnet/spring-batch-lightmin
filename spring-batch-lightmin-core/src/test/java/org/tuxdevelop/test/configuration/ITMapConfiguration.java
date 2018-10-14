package org.tuxdevelop.test.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.tuxdevelop.spring.batch.lightmin.configuration.EnableSpringBatchLightmin;
import org.tuxdevelop.spring.batch.lightmin.configuration.SpringBatchLightminConfigurationProperties;
import org.tuxdevelop.spring.batch.lightmin.repository.annotation.EnableLightminMapConfigurationRepository;

@Configuration
@EnableSpringBatchLightmin
@EnableLightminMapConfigurationRepository
@EnableConfigurationProperties(value = {SpringBatchLightminConfigurationProperties.class})
@PropertySource("classpath:properties/map.properties")
@Import(value = {ITJobConfiguration.class})
public class ITMapConfiguration {

}
