package org.tuxdevelop.spring.batch.lightmin.client.configuration;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.tuxdevelop.spring.batch.lightmin.client.api.controller.LightminClientApplicationRestController;
import org.tuxdevelop.spring.batch.lightmin.client.event.JobExecutionEventPublisher;
import org.tuxdevelop.spring.batch.lightmin.client.listener.OnJobExecutionFinishedEventListener;
import org.tuxdevelop.spring.batch.lightmin.client.service.LightminClientApplicationService;
import org.tuxdevelop.spring.batch.lightmin.client.service.LightminServerLocatorService;
import org.tuxdevelop.spring.batch.lightmin.configuration.CommonSpringBatchLightminConfiguration;
import org.tuxdevelop.spring.batch.lightmin.util.BasicAuthHttpRequestInterceptor;

import java.util.Collections;

/**
 * @author Marcel Becker
 * @since 0.3
 */
@Configuration
@EnableConfigurationProperties(value = {
        LightminClientProperties.class,
        LightminClientServerProperties.class})
@Import(value = {CommonSpringBatchLightminConfiguration.class})
public class LightminClientConfiguration {


    @Bean
    @ConditionalOnProperty(
            prefix = "spring.batch.lightmin.client",
            value = "publish-job-events",
            havingValue = "true",
            matchIfMissing = true)
    public JobExecutionEventPublisher jobExecutionEventPublisher(
            final LightminServerLocatorService lightminServerLocatorService,
            final LightminClientServerProperties lightminProperties) {
        final RestTemplate restTemplate = LightminServerRestTemplateFactory.getRestTemplate(lightminProperties);
        return new JobExecutionEventPublisher(restTemplate, lightminServerLocatorService);
    }

    @Bean
    public OnJobExecutionFinishedEventListener onJobExecutionFinishedEventListener(
            final JobExecutionEventPublisher jobExecutionEventPublisher) {
        return new OnJobExecutionFinishedEventListener(jobExecutionEventPublisher);
    }

    @Bean
    public LightminClientApplicationService lightminClientApplicationService(
            final JobRegistry jobRegistry,
            final LightminClientProperties lightminClientProperties) {
        return new LightminClientApplicationService(jobRegistry, lightminClientProperties);
    }

    @Bean
    public LightminClientApplicationRestController lightminClientApplicationRestController(final LightminClientApplicationService lightminClientApplicationService) {
        return new LightminClientApplicationRestController(lightminClientApplicationService);
    }

    public static class LightminServerRestTemplateFactory {

        private static RestTemplate restTemplate;

        public static RestTemplate getRestTemplate(final LightminClientServerProperties lightminProperties) {

            if (restTemplate == null) {
                restTemplate = new RestTemplate();
            }
            if (lightminProperties.getUsername() != null) {
                restTemplate.setInterceptors(
                        Collections.singletonList(
                                new BasicAuthHttpRequestInterceptor(lightminProperties.getUsername(),
                                        lightminProperties.getPassword()))
                );
            }
            return restTemplate;
        }
    }
}
