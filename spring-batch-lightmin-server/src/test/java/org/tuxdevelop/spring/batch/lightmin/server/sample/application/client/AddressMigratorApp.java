package org.tuxdevelop.spring.batch.lightmin.server.sample.application.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
@PropertySource(value = "classpath:properties/sample/client/client.properties")
@ComponentScan(basePackages = "org.tuxdevelop.spring.batch.lightmin.server.sample.application.client")
public class AddressMigratorApp {

    public static void main(final String[] args) {
        SpringApplication.run(AddressMigratorApp.class, args);
    }

}
