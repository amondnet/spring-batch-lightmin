package org.tuxdevelop.spring.batch.lightmin.client.listener;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.tuxdevelop.spring.batch.lightmin.client.service.LightminClientApplicationRegistrationService;

public class OnContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    private final LightminClientApplicationRegistrationService registrationLightminClientApplicationBean;

    public OnContextClosedEventListener(
            final LightminClientApplicationRegistrationService registrationLightminClientApplicationBean) {
        this.registrationLightminClientApplicationBean = registrationLightminClientApplicationBean;
    }

    @Override
    public void onApplicationEvent(final ContextClosedEvent event) {
        this.registrationLightminClientApplicationBean.stopRegisterTask();
    }
}
