package com.bluewhale.medlog.medintakesnapshot.token;

import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PolicyRequestToken {

    private final Long medId;
    private final Long appUserId;
    private final DoseFrequency doseFrequency;
    private final List<PolicyRequestMedIntakeToken> policyRequestMedIntakeTokenList;
    private final LocalDate startedOn;
    private final LocalDate endedOn;
    private final LocalDate referenceDate;

    public static PolicyRequestToken from(Med entity) {
        return from(entity, LocalDate.now());
    }

    public static PolicyRequestToken from(Med entity, LocalDate referenceDate) {
        if (entity == null) {
            throw new IllegalArgumentException("Med entity cannot be null");
        }

        /*
            약에 대한 복용기록이 없을 수도 있으므로 null 체크 (신규 약 등록 시)
         */
        List<PolicyRequestMedIntakeToken> intakeTokenList =
                entity.getMedIntakeRecordList() != null ?
                entity.getMedIntakeRecordList().stream()
                        .map(PolicyRequestMedIntakeToken::from)
                        .toList() :
                List.of();

        return new PolicyRequestToken(
                entity.getMedId(),
                entity.getAppUser().getAppUserId(),
                entity.getDoseFrequency(),
                intakeTokenList,
                entity.getStartedOn(),
                entity.getEndedOn(),
                referenceDate
        );
    }



    @Builder(access = AccessLevel.PRIVATE)
    public record PolicyRequestMedIntakeToken(
            Long medIntakeRecordId, Long medId, boolean isTaken, LocalDateTime estimatedDoseTime
    ) {

    public static PolicyRequestMedIntakeToken from(MedIntakeRecord entity) {
            return PolicyRequestMedIntakeToken.builder()
                    .medIntakeRecordId(entity.getMedIntakeRecordId())
                    .medId(entity.getMed().getMedId())
                    .isTaken(entity.isTaken())
                    .estimatedDoseTime(entity.getEstimatedDoseTime())
                    .build();
        }
    }

    public static PolicyRequestToken getCopyFrom(PolicyRequestToken policyRequestToken, LocalDate newReferenceDate) {
        if (policyRequestToken == null) {
            throw new IllegalArgumentException("PolicyRequestToken cannot be null");
        }
        if (newReferenceDate == null) {
            throw new IllegalArgumentException("New reference date cannot be null");
        }

        return new PolicyRequestToken(
                policyRequestToken.getMedId(),
                policyRequestToken.getAppUserId(),
                policyRequestToken.getDoseFrequency(),
                policyRequestToken.getPolicyRequestMedIntakeTokenList(),
                policyRequestToken.getStartedOn(),
                policyRequestToken.getEndedOn(),
                newReferenceDate
        );

    }
}
