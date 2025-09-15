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
    public List<PolicyEvaluateResult> evaluate(PolicyRequestToken policyRequestToken) {
        // 입력값 검증
        if (policyRequestToken == null) {
            throw new IllegalArgumentException("policyRequestToken should not be null or empty");
        }

        boolean isEndedOnExists = policyRequestToken.getEndedOn() != null;
        boolean isEndedOnInOneYear =
                isEndedOnExists &&
                policyRequestToken.getStartedOn().plusYears(1L).isAfter(policyRequestToken.getEndedOn());

        int creationDays =
                isEndedOnExists ?
                (
                        isEndedOnInOneYear ?
                        policyRequestToken.getStartedOn().until(policyRequestToken.getEndedOn()).getDays() + 1 :
                        365
                ) :
                defaultAfterDays;

        log.info("Evaluating policy for {} days...", creationDays);

        PolicyRequestToken requestToken;
        List<PolicyEvaluateResult> resultList = new ArrayList<>();
        for (int i = 0; i < creationDays; i++) {
            requestToken = PolicyRequestToken.getCopyFrom(policyRequestToken, policyRequestToken.getStartedOn().plusDays(i));
            log.info("Evaluating policy for reference date: {}", requestToken.getReferenceDate());
            // 실제 평가 수행
            List<PolicyEvaluateResult> policyEvaluateResultList = doEvaluate(requestToken);
            resultList.addAll(policyEvaluateResultList);
        }
        log.info("Total {} results evaluated.", resultList.size());
        // 스냅샷 생성이 필요한 결과만 필터링하여 반환
        return resultList.stream().filter(PolicyEvaluateResult::isShouldTake).toList();
    }

    private List<PolicyEvaluateResult> doEvaluate(PolicyRequestToken policyRequestToken) {
        log.info("Finding provider...");
        SnapshotPolicyProvider provider = providerList.stream()
                .filter(
                        (p) -> p.supports(policyRequestToken.getDoseFrequency().getDoseFrequencyType())
                )
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException(String.format("No supported provider found for policy token '%s'", policyRequestToken))
                );
        log.info("Found proper provider : {}", provider.getClass().getSimpleName());

        // provider 에게 평가 위임
        List<PolicyEvaluateResult> resultList = provider.evaluate(policyRequestToken, policyRequestToken.getReferenceDate());
        log.info("Policy evaluation done by provider.");
        return resultList;
    }
}
