package com.bluewhale.medlog.medintakerecord.domain.entity;

import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.persistence.MedUuidConverter;
import com.bluewhale.medlog.medintakerecord.domain.persistence.MedIntakeRecordUuidConverter;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordRegisterDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Table(name = "med_intake_record")
public class MedIntakeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medIntakeRecordId;

    @Convert(converter = MedIntakeRecordUuidConverter.class)
    private MedIntakeRecordUuid medIntakeRecordUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "med_id")
    private Med med;

    private boolean isTaken;

    private LocalDateTime estimatedDoseTime;

    private LocalDateTime takenAt;

    public static MedIntakeRecord create(MedIntakeRecordRegisterDTO dto, Med med) {
        return MedIntakeRecord.builder()
                .medIntakeRecordId(null)
                .medIntakeRecordUuid(new MedIntakeRecordUuid(UUID.randomUUID().toString()))
                .med(med)
                .isTaken(dto.isTaken())
                .estimatedDoseTime(dto.getEstimatedDoseTime())
                .takenAt(dto.getTakenAt())
                .build();
    }
}
