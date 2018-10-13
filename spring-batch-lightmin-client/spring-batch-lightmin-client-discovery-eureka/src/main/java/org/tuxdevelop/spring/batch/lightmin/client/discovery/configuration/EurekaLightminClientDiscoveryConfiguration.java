package org.tuxdevelop.spring.batch.lightmin.client.discovery.configuration;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.EurekaClientConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.tuxdevelop.spring.batch.lightmin.client.discovery.lifecycle.LightminEurekaClientConfigBean;
import org.tuxdevelop.spring.batch.lightmin.client.discovery.metadata.EurekaMetaDataExtender;
import org.tuxdevelop.spring.batch.lightmin.client.discovery.metadata.MetaDataExtender;

@Configuration
@Import(value = {LightminClientDiscoveryConfiguration.class})
@ConditionalOnProperty(value = "eureka.client.enabled", matchIfMissing = true)
public class EurekaLightminClientDiscoveryConfiguration {

    @Bean
    public MetaDataExtender metaDataExtender(final ApplicationInfoManager applicationInfoManager) {
        return new EurekaMetaDataExtender(applicationInfoManager);
    }

    @Bean
    public EurekaClientConfig eurekaClientConfig(final MetaDataExtender metaDataExtender) {
        return new LightminEurekaClientConfigBean(metaDataExtender);
    }
}