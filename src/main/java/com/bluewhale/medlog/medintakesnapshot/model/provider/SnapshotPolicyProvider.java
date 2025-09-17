package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestToken;

import java.time.LocalDate;
import java.util.List;

public interface SnapshotPolicyProvider {

    boolean supports(DoseFrequencyType type);
    List<PolicyEvaluateResult> evaluate(PolicyRequestToken policyReqMedToken, LocalDate stdDate);

}
