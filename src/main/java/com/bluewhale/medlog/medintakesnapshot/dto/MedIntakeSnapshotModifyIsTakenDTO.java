package com.bluewhale.medlog.medintakesnapshot.dto;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordRegisterDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MedIntakeSnapshotModifyIsTakenDTO {

    private final MedUuid medUuid;
    private final boolean isTaken;
    private final LocalDateTime estimatedDoseTime;

    public static MedIntakeSnapshotModifyIsTakenDTO from(MedIntakeRecordDTO medIntakeRecordDTO) {
        return MedIntakeSnapshotModifyIsTakenDTO.builder()
                .medUuid(medIntakeRecordDTO.getMedUuid())
                .isTaken(medIntakeRecordDTO.isTaken())
                .estimatedDoseTime(medIntakeRecordDTO.getEstimatedDoseTime())
                .build();
    }
}
