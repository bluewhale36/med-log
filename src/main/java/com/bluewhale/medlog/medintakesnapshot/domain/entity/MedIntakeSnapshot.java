package com.bluewhale.medlog.medintakesnapshot.domain.entity;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.medintakesnapshot.domain.persistence.PolicyTracerConverter;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Table(name = "med_intake_snapshot")
public class MedIntakeSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medIntakeSnapshotId;

    private LocalDate snapshotDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "med_id")
    private Med med;

    private boolean shouldTake;

    private boolean isTaken;

    private LocalDateTime estimatedDoseTime;

    @Column(columnDefinition = "JSON", name="policy_reason")
    @Convert(converter = PolicyTracerConverter.class)
    private PolicyEvaluateTracer policyReason;

    private LocalDateTime evaluatedAt;
}
