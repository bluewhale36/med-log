package com.bluewhale.medlog.medintakesnapshot.model.manager;

import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestToken;

import java.util.List;

public interface SnapshotPolicyManager {

    Integer defaultAfterDays = 180;

    List<PolicyEvaluateResult> evaluate(PolicyRequestToken policyReqTokenList);

}
