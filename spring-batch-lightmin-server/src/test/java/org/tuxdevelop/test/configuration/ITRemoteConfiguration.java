package org.tuxdevelop.test.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.tuxdevelop.spring.batch.lightmin.server.configuration.EnableSpringBatchLightminServer;

@Configuration
@EnableSpringBatchLightminServer
@Import(value = {ITJobConfiguration.class})
public class ITRemoteConfiguration {
}
