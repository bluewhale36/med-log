package com.bluewhale.medlog.medintakesnapshot.model.provider;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateResult;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedIntakeToken;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedToken;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractPolicyProvider implements SnapshotPolicyProvider {

    private final DoseFrequencyType supportingDoseFrequencyType;

    protected AbstractPolicyProvider(DoseFrequencyType supportingDoseFrequencyType) {
        this.supportingDoseFrequencyType = supportingDoseFrequencyType;
    }

    @Override
    public boolean supports(DoseFrequencyType type) {
        return type.equals(supportingDoseFrequencyType);
    }

    /**
     * Snapshot 생성 과정에서 전처리가 필요한 여부를 나타냄. 상속하는 Provider 에서 전처리가 필요 없을 경우 재정의.
     * @return 전처리가 필요할 경우 {@code true}, 그렇지 않을 경우 {@code false}.
     */
    protected boolean isNeedPreProcess() {
        return true;
    }

    @Override
    public List<PolicyEvaluateResult> evaluate(PolicyRequestMedToken prmToken, LocalDate stdDate) {
        log.info("\n\n ============== evaluate ============== \n\n");
        log.info(" prmToken: {}", prmToken);
        log.info(" stdDate: {}", stdDate);
        PolicyEvaluateTracer tracer = new PolicyEvaluateTracer();
        List<PolicyEvaluateResult> resultList = new ArrayList<>();

        // 전처리 필요한 경우
        if (isNeedPreProcess()) {
            log.info("Pre process Needed");
            tracer.setPreProcessNeeded(true);
            // tracer 의 내부 클래스 인스턴스화
            tracer.setPreProcess(new PolicyEvaluateTracer.PreProcess());
            preProcess(tracer, prmToken, stdDate);

            // 기준 일자가 약의 복용 기간 내에 포함되지 않는 경우.
            if (!tracer.getPreProcess().getIsStdDateInMedDuration()) {
                log.info("StdDate not included in Med Duration");
                tracer.setSpecificProcessNeeded(false);
                tracer.setReason(null);

                resultList.add(new PolicyEvaluateResult(
                        null, prmToken.getMedId(), false, null, stdDate, tracer
                        )
                );
                return resultList;
            }
        }
        log.info("Trying to get time list");
        // 약의 복용 시각 리스트 반환.
        List<LocalTime> timeList = getTimeListOfDoseFrequencyDetail(prmToken).orElse(null);

        if (timeList != null) {
            log.info("Time list found");
            // stdDate 기준으로 DateTime 으로 변환.
            List<LocalDateTime> timeListAsDateTime = timeList.stream().map((t) -> LocalDateTime.of(stdDate, t)).toList();

            for (LocalDateTime stdDateTime : timeListAsDateTime) {
                log.info("stdDateTime: {}", stdDateTime);
                // tracer 인스턴스 복사
                PolicyEvaluateTracer specificTracer = PolicyEvaluateTracer.copyOf(tracer);

                log.info("Trying to find taken record on stdDateTime: {}", stdDateTime);
                // 약 복용 예정 시각에 대해 이미 복용 기록이 있는 여부
                specificTracer.getPreProcess().setHasTakenRecordOnStdDate(
                        hasTakenRecordOnStdDateTime(prmToken.getPolicyReqMedIntakeTokenList(), stdDateTime)
                );
                if (specificTracer.getPreProcess().getHasTakenRecordOnStdDate()) {
                    log.info("Taken record found");
                    // 이미 복용 기록 있을 경우 세부 사항 확인 필요 없음.
                    specificTracer.setSpecificProcessNeeded(false);
                    specificTracer.setReason(null);

                    resultList.add(new PolicyEvaluateResult(
                            null, prmToken.getMedId(), false, stdDateTime, stdDate, specificTracer
                            )
                    );
                } else {
                    log.info("Taken record not found");
                    log.info("Specific evaluation needed");
                    // 복용 기록이 없는 경우 세부 사항 확인 필요.
                    specificTracer.setSpecificProcessNeeded(true);
                    resultList.add(doEvaluate(specificTracer, prmToken, stdDateTime));
                }
            }
        } else {
            log.info("No time list found");
            // timeList 를 가지지 않거나 더 세부의 기준이 있는 경우.
            // 공통 조건에 따라 분기해서 결과 생성 필요.
            resultList.add(doEvaluate(tracer, prmToken, LocalDateTime.of(stdDate, LocalTime.of(0, 0))));
        }
        return resultList;
    }

    private void preProcess(PolicyEvaluateTracer tracer, PolicyRequestMedToken prmToken, LocalDate stdDate) {
        Boolean duration = isStdDateInMedDuration(prmToken.getStartedOn(), prmToken.getEndedOn(), stdDate);
        tracer.getPreProcess().setIsStdDateInMedDuration(duration);
    }

    /**
     * Snapshot 을 생성하는 기준 일자가 약의 복용 기간 내에 포함된 여부 반환.
     * @param startedOn 약 복용 시작 일자
     * @param endedOn 약 복용 종료 일자
     * @param stdDate Snapshot 생성의 기준 일자
     * @return 기준 일자가 시작일자와 같거나 이후일 경우, 종료 일자가 없거나 기준 일자가 종료 일자와 같거나 이전인 경우 {@code true} 반환.
     */
    private boolean isStdDateInMedDuration(LocalDate startedOn, @Nullable LocalDate endedOn, LocalDate stdDate) {
        return (startedOn.isEqual(stdDate) || startedOn.isBefore(stdDate)) && (endedOn == null || (endedOn.isEqual(stdDate) || endedOn.isAfter(stdDate)));
    }

    /**
     * Snapshot 을 생성하는 기준 일자에 대해, 약의 예정 복용 시각에 대한 복용 기록이 존재하는 여부 반환.
     * @param prmiTokenList 약에 대한 복용 기록 리스트.
     * @param stdDateTime 기준 일자에 대한 약의 예정 복용 일시.
     * @return 복용 예정 일시에 대한 복용 기록이 이미 존재하는 경우 {@code true}, 그렇지 않을 경우 {@code false} 반환.
     */
    private boolean hasTakenRecordOnStdDateTime(List<PolicyRequestMedIntakeToken> prmiTokenList, LocalDateTime stdDateTime) {
        if (prmiTokenList == null || prmiTokenList.isEmpty()) {
            return false;
        } else {
            return prmiTokenList.stream()
                    .map(PolicyRequestMedIntakeToken::getEstimatedDoseTime)
                    .anyMatch((estimatedDoseTime) -> estimatedDoseTime.isEqual(stdDateTime));
        }
    }

    protected abstract Optional<List<LocalTime>> getTimeListOfDoseFrequencyDetail(PolicyRequestMedToken prmToken);

    /**
     * 기준 일시에 대해 Snapshot 생성.
     * @param specificTracer
     * @param policyReqMedToken
     * @param stdDateTime
     * @return
     */
    protected abstract PolicyEvaluateResult doEvaluate(PolicyEvaluateTracer specificTracer, PolicyRequestMedToken policyReqMedToken, LocalDateTime stdDateTime);



}
