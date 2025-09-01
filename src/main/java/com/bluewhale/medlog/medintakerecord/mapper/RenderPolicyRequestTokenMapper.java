package com.bluewhale.medlog.medintakerecord.mapper;

import com.bluewhale.medlog.medintakerecord.model.RenderPolicyRequestTokenForMedIntakeRecord;
import com.bluewhale.medlog.medintakerecord.model.RenderPolicyRequestTokenForMedIntakeSnapshot;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RenderPolicyRequestTokenMapper {

    public RenderPolicyRequestTokenForMedIntakeRecord forMedIntakeRecord(
            Long medIntakeRecordId, Long medId, LocalDateTime estimatedDoseTime
    ) {
        return new RenderPolicyRequestTokenForMedIntakeRecord(
                medIntakeRecordId, medId, estimatedDoseTime
        );
    }

    public RenderPolicyRequestTokenForMedIntakeSnapshot forMedIntakeSnapshot(
            Long medIntakeSnapshotId, Long medId, LocalDateTime estimatedDoseTime
    ) {
        return new RenderPolicyRequestTokenForMedIntakeSnapshot(
                medIntakeSnapshotId, medId, estimatedDoseTime
        );
    }

}
