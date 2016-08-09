package org.opensrp.register.service.handler;

import org.opensrp.common.AllConstants;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.service.VaksinatorService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaksinatorCampakHandler implements FormSubmissionHandler {
    private VaksinatorService vaksinatorService;

    @Autowired
    public VaksinatorCampakHandler(VaksinatorService vaksinatorservice) {
        this.vaksinatorService = vaksinatorservice;
    }

    @Override
    public void handle(FormSubmission submission) {
        vaksinatorService.immunizationGiven(submission, AllConstants.Form.CAMPAK_VISIT);
    }
}
