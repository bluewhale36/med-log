package com.bluewhale.medlog.medintakesnapshot.model.manager;

import com.bluewhale.medlog.medintakesnapshot.model.provider.SnapshotPolicyProvider;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedToken;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class PolicyManager implements SnapshotPolicyManager {

    private final List<SnapshotPolicyProvider> providerList;

    @Override
    public List<PolicyEvaluateResult> evaluate(List<PolicyRequestToken> policyReqTokenList) {
        if (policyReqTokenList == null || policyReqTokenList.isEmpty()) {
            throw new IllegalArgumentException("policyReqTokenList should not be null or empty");
        }

        List<PolicyEvaluateResult> resultList = new ArrayList<>();
        log.info("\n\n ============== evaluate ============== \n\n");
        for (PolicyRequestToken policyReqToken : policyReqTokenList) {
            List<PolicyEvaluateResult> policyEvaluateResultList = evaluate(policyReqToken);
            resultList.addAll(policyEvaluateResultList);
        }

        return resultList.stream().filter(PolicyEvaluateResult::isShouldTake).toList();
    }

    private List<PolicyEvaluateResult> evaluate(PolicyRequestToken policyReqToken) {
        List<PolicyEvaluateResult> resultList = new ArrayList<>();

        for (PolicyRequestMedToken policyReqMedToken : policyReqToken.getPolicyReqMedTokenList()) {
            log.info("Evaluation started for policyReqMedToken: {}", policyReqMedToken);
            SnapshotPolicyProvider provider = providerList.stream()
                    .filter(
                            (p) -> p.supports(policyReqMedToken.getDoseFrequency().getDoseFrequencyType())
                    )
                    .findFirst()
                    .orElseThrow(
                            () -> new IllegalStateException(String.format("No supported provider found for policy token '%s'", policyReqMedToken))
                    );
            log.info("selected provider '{}'", provider);
            provider.evaluate(policyReqMedToken, policyReqToken.getStdDate()).stream()
                    .map(
                            (res) -> copyResult(res, policyReqToken.getAppUserId())
                    )
                    .forEach(resultList::add);
        }
        return resultList;
    }

    private PolicyEvaluateResult copyResult(PolicyEvaluateResult policyEvaluateResult, Long appUserId) {
        return new PolicyEvaluateResult(
                appUserId,
                policyEvaluateResult.getMedId(),
                policyEvaluateResult.isShouldTake(),
                policyEvaluateResult.getEstimatedDoseTime(),
                policyEvaluateResult.getStdDate(),
                policyEvaluateResult.getReason()
        );
    }
}
