package com.bluewhale.medlog.medintakerecord.domain.entity;

import com.bluewhale.medlog.med.domain.persistence.MedUuidConverter;
import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Convert(converter = MedUuidConverter.class)
    private MedIntakeRecordUuid medIntakeRecordUuid;

    @Column(name = "med_id")
    private Long medId;

    private boolean isTaken;

    private LocalDateTime estimatedDoseTime;

    private LocalDateTime takenAt;
}
