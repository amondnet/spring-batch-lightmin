package org.tuxdevelop.spring.batch.lightmin.server.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.tuxdevelop.spring.batch.lightmin.api.resource.admin.JobIncrementer;
import org.tuxdevelop.spring.batch.lightmin.api.resource.batch.JobLaunch;
import org.tuxdevelop.spring.batch.lightmin.api.resource.common.JobParameter;
import org.tuxdevelop.spring.batch.lightmin.api.resource.common.JobParameters;
import org.tuxdevelop.spring.batch.lightmin.api.resource.common.ParameterType;
import org.tuxdevelop.spring.batch.lightmin.client.api.LightminClientApplication;
import org.tuxdevelop.spring.batch.lightmin.model.JobLauncherModel;
import org.tuxdevelop.spring.batch.lightmin.model.JobNameModel;
import org.tuxdevelop.spring.batch.lightmin.server.job.JobServerService;
import org.tuxdevelop.spring.batch.lightmin.server.support.RegistrationBean;
import org.tuxdevelop.spring.batch.lightmin.util.ParameterParser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcel Becker
 * @since 0.1
 */
@Controller
@RequestMapping()
public class JobLauncherController extends CommonController {

    private final JobServerService jobServerService;
    private final RegistrationBean registrationBean;

    @Autowired
    public JobLauncherController(final JobServerService jobServerService,
                                 final RegistrationBean registrationBean) {
        this.jobServerService = jobServerService;
        this.registrationBean = registrationBean;
    }

    @RequestMapping(value = "/jobLaunchers", method = RequestMethod.GET)
    public void initJobLaunchers(final Model model,
                                 @RequestParam(value = "applicationid") final String applicationid) {
        final LightminClientApplication lightminClientApplication = this.registrationBean.get(applicationid);
        model.addAttribute("jobNames", lightminClientApplication.getLightminClientInformation().getRegisteredJobs());
        model.addAttribute("jobName", new JobNameModel());
        model.addAttribute("clientApplication", lightminClientApplication);
    }

    @RequestMapping(value = "/jobLauncher", method = RequestMethod.GET)
    public void configureJobLauncher(@RequestParam(value = "jobName") final String jobName,
                                     @RequestParam(value = "id") final String applicationId,
                                     final Model model) {
        final LightminClientApplication lightminClientApplication = this.registrationBean.get(applicationId);
        final JobLauncherModel jobLauncherModel = new JobLauncherModel();
        final JobParameters oldJobParameters = this.jobServerService.getLastJobParameters(jobName, lightminClientApplication);
        jobLauncherModel.setJobName(jobName);
        jobLauncherModel.setJobParameters(ParameterParser.parseParametersToString(oldJobParameters));
        model.addAttribute("jobLauncherModel", jobLauncherModel);
        model.addAttribute("jobIncrementers",
                lightminClientApplication.getLightminClientInformation().getSupportedJobIncrementers());
        model.addAttribute("clientApplication", lightminClientApplication);
    }

    @RequestMapping(value = "/jobLauncher", method = RequestMethod.POST)
    public RedirectView launchJob(@ModelAttribute("jobLauncherModel") final JobLauncherModel jobLauncherModel,
                                  @RequestParam("id") final String applicationId,
                                  final HttpServletRequest request) {
        final LightminClientApplication lightminClientApplication = this.registrationBean.get(applicationId);
        final String jobName = jobLauncherModel.getJobName();
        final JobParameters jobParameters = ParameterParser
                .parseParametersStringToJobParameters(jobLauncherModel.getJobParameters());
        final JobLaunch jobLaunch = new JobLaunch();
        this.attacheIncremeter(jobLauncherModel, jobParameters);
        jobLaunch.setJobName(jobName);
        jobLaunch.setJobParameters(jobParameters);
        this.jobServerService.launchJob(jobLaunch, lightminClientApplication);

        return this.createRedirectView("job?jobname=" + jobName + "&applicationid=" + applicationId, request);
    }

    void attacheIncremeter(final JobLauncherModel jobLauncherModel, final JobParameters jobParameters) {
        final JobIncrementer jobIncrementer = jobLauncherModel.getJobIncrementer();
        if (JobIncrementer.DATE.equals(jobIncrementer)) {
            final JobParameter jobParameter = new JobParameter();
            jobParameter.setParameter(System.currentTimeMillis());
            jobParameter.setParameterType(ParameterType.LONG);
            jobParameters.getParameters().put(jobIncrementer.getIncrementerIdentifier(), jobParameter);
        }
    }

}
