package com.bluewhale.medlog.medintakesnapshot.model.manager;

import com.bluewhale.medlog.medintakesnapshot.model.provider.SnapshotPolicyProvider;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
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
    public List<PolicyEvaluateResult> evaluate(List<PolicyRequestToken> policyRequestTokenList) {
        // 입력값 검증
        if (policyRequestTokenList == null || policyRequestTokenList.isEmpty()) {
            throw new IllegalArgumentException("policyRequestTokenList should not be null or empty");
        }

        List<PolicyEvaluateResult> resultList = new ArrayList<>();
        for (PolicyRequestToken requestToken : policyRequestTokenList) {
            List<PolicyEvaluateResult> policyEvaluateResultList = evaluate(requestToken);
            resultList.addAll(policyEvaluateResultList);
        }
        log.info("Total {} results evaluated from {} request tokens.", resultList.size(), policyRequestTokenList.size());
        // 스냅샷 생성이 필요한 결과만 필터링하여 반환
        return resultList.stream().filter(PolicyEvaluateResult::isShouldTake).toList();
    }

    private List<PolicyEvaluateResult> evaluate(PolicyRequestToken requestToken) {
        log.info("Finding provider...");
        SnapshotPolicyProvider provider = providerList.stream()
                .filter(
                        (p) -> p.supports(requestToken.getDoseFrequency().getDoseFrequencyType())
                )
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException(String.format("No supported provider found for policy token '%s'", requestToken))
                );
        log.info("Found proper provider : {}", provider.getClass().getSimpleName());

        // provider 에게 평가 위임
        List<PolicyEvaluateResult> resultList = provider.evaluate(requestToken, requestToken.getReferenceDate());
        log.info("Policy evaluation done by provider.");
        return resultList;
    }
}
