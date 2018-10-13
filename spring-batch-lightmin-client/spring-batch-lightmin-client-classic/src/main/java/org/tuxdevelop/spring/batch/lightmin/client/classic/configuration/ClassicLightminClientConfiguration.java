package org.tuxdevelop.spring.batch.lightmin.client.classic.configuration;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.tuxdevelop.spring.batch.lightmin.client.classic.event.OnClientApplicationReadyEventListener;
import org.tuxdevelop.spring.batch.lightmin.client.classic.event.OnContextClosedEventListener;
import org.tuxdevelop.spring.batch.lightmin.client.classic.service.LightminClientApplicationRegistrationService;
import org.tuxdevelop.spring.batch.lightmin.client.classic.service.LightminClientRegistratorService;
import org.tuxdevelop.spring.batch.lightmin.client.classic.service.UrlLightminServerLocatorService;
import org.tuxdevelop.spring.batch.lightmin.client.configuration.LightminClientConfiguration;
import org.tuxdevelop.spring.batch.lightmin.client.configuration.LightminClientProperties;
import org.tuxdevelop.spring.batch.lightmin.client.configuration.LightminClientServerProperties;
import org.tuxdevelop.spring.batch.lightmin.client.service.LightminServerLocatorService;

@Configuration
@Import(value = {LightminClientConfiguration.class})
public class ClassicLightminClientConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = {LightminClientRegistratorService.class})
    public LightminClientRegistratorService lightminClientRegistratorService(
            final LightminClientProperties lightminClientProperties,
            final LightminClientServerProperties lightminProperties,
            final JobRegistry jobRegistry,
            final LightminServerLocatorService lightminServerLocatorService) {
        final RestTemplate restTemplate =
                LightminClientConfiguration.LightminServerRestTemplateFactory.getRestTemplate(lightminProperties);
        return new LightminClientRegistratorService(
                lightminClientProperties, lightminProperties, restTemplate, jobRegistry, lightminServerLocatorService);
    }

    /**
     * ApplicationListener triggering registration after being ready/shutdown
     */
    @Bean
    @ConditionalOnMissingBean(value = {LightminClientApplicationRegistrationService.class})
    public LightminClientApplicationRegistrationService lightminClientApplicationRegistrationService(
            final LightminClientRegistratorService lightminClientRegistrator,
            final LightminClientServerProperties lightminClientServerProperties) {
        final LightminClientApplicationRegistrationService registrationLightminClientApplicationBean =
                new LightminClientApplicationRegistrationService(lightminClientRegistrator);
        registrationLightminClientApplicationBean.setAutoRegister(lightminClientServerProperties.isAutoRegistration());
        registrationLightminClientApplicationBean.setAutoDeregister(lightminClientServerProperties.isAutoDeregistration());
        registrationLightminClientApplicationBean.setRegisterPeriod(lightminClientServerProperties.getPeriod());
        return registrationLightminClientApplicationBean;
    }


    @Bean
    @ConditionalOnMissingBean(value = {LightminServerLocatorService.class})
    public LightminServerLocatorService urlLightminServerLocator(
            final LightminClientServerProperties lightminClientServerProperties) {
        return new UrlLightminServerLocatorService(lightminClientServerProperties);
    }

    @Bean
    public OnClientApplicationReadyEventListener onClientApplicationReadyEventListener(
            final LightminClientApplicationRegistrationService lightminClientApplicationRegistrationService,
            final LightminClientProperties lightminClientProperties) {
        return new OnClientApplicationReadyEventListener(
                lightminClientApplicationRegistrationService, lightminClientProperties);
    }

    @Bean
    public OnContextClosedEventListener onContextClosedEventListener(
            final LightminClientApplicationRegistrationService lightminClientApplicationRegistrationService) {
        return new OnContextClosedEventListener(lightminClientApplicationRegistrationService);
    }

}
