package org.tuxdevelop.test.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.tuxdevelop.spring.batch.lightmin.client.discovery.configuration.EurekaLightminClientDiscoveryConfiguration;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(value = {EurekaLightminClientDiscoveryConfiguration.class})
public class EurekaITConfiguration {

    public static void main(final String[] args) {
        SpringApplication.run(EurekaITConfiguration.class, args);
    }

}
