package com.bluewhale.medlog.medintakesnapshot.token;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
public class PolicyRequestMedToken {

    private final Long medId;
    private final DoseFrequency doseFrequency;
    private final List<PolicyRequestMedIntakeToken> policyReqMedIntakeTokenList;
    private final LocalDate startedOn;
    private final LocalDate endedOn;

    private PolicyRequestMedToken(Long medId, DoseFrequency doseFrequency, List<PolicyRequestMedIntakeToken> policyReqMedIntakeTokenList, LocalDate startedOn, LocalDate endedOn) {
        this.medId = medId;
        this.doseFrequency = doseFrequency;
        this.policyReqMedIntakeTokenList = policyReqMedIntakeTokenList;
        this.startedOn = startedOn;
        this.endedOn = endedOn;
    }

    public static PolicyRequestMedToken of(Long medId, DoseFrequency doseFrequency, List<PolicyRequestMedIntakeToken> policyReqMedIntakeTokenList, LocalDate startedOn, LocalDate endedOn) {
        long aCount = policyReqMedIntakeTokenList.stream()
                .filter((prmiToken) -> !prmiToken.getMedId().equals(medId))
                .count();

        if (aCount > 0) {
            throw new IllegalArgumentException("PolicyRequestMedIntakeToken must have same medId with PolicyRequestMedToken.");
        }

        return new PolicyRequestMedToken(medId, doseFrequency, policyReqMedIntakeTokenList, startedOn, endedOn);
    }
}
