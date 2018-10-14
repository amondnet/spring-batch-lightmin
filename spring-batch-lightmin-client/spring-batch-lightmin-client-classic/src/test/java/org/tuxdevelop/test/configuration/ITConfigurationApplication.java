package org.tuxdevelop.test.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.tuxdevelop.spring.batch.lightmin.client.classic.configuration.ClassicLightminClientConfiguration;
import org.tuxdevelop.spring.batch.lightmin.repository.annotation.EnableLightminMapConfigurationRepository;

@EnableAutoConfiguration
@EnableLightminMapConfigurationRepository
@Import(value = {ClassicLightminClientConfiguration.class})
public class ITConfigurationApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ITConfigurationApplication.class, args);
    }
}
