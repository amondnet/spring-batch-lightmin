package org.tuxdevelop.spring.batch.lightmin.client.classic.service;

import lombok.extern.slf4j.Slf4j;
import org.tuxdevelop.spring.batch.lightmin.client.configuration.LightminClientServerProperties;
import org.tuxdevelop.spring.batch.lightmin.client.service.LightminServerLocatorService;
import org.tuxdevelop.spring.batch.lightmin.exception.SpringBatchLightminConfigurationException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marcel Becker
 * @since 0.5
 */
@Slf4j
public class UrlLightminServerLocatorService implements LightminServerLocatorService {

    private final LightminClientServerProperties lightminProperties;

    public UrlLightminServerLocatorService(final LightminClientServerProperties lightminProperties) {
        this.lightminProperties = lightminProperties;
    }

    @Override
    public List<String> getRemoteUrls() {
        final List<String> urls;
        if (this.lightminProperties.getLightminUrl() == null || this.lightminProperties.getLightminUrl().length == 0) {
            throw new SpringBatchLightminConfigurationException("The lightmin server urls must not be null or empty");
        } else {
            urls = Arrays.asList(this.lightminProperties.getLightminUrl());
            log.debug("Configured lightmin server urls {}", urls);
        }
        return urls;
    }
}
