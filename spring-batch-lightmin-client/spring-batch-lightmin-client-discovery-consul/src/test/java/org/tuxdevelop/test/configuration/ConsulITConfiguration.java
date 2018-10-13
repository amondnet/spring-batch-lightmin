package org.tuxdevelop.test.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.tuxdevelop.spring.batch.lightmin.client.discovery.configuration.ConsulLightminClientDiscoveryConfiguration;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(value = {ConsulLightminClientDiscoveryConfiguration.class})
public class ConsulITConfiguration {

    public static void main(final String[] args) {
        SpringApplication.run(ConsulITConfiguration.class, args);
    }

}
