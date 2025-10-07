package com.bluewhale.medlog.med.domain.entity;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.med.domain.persistence.DoseFrequencyConverter;
import com.bluewhale.medlog.med.domain.persistence.MedUuidConverter;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.dto.MedRegisterDTO;
import com.bluewhale.medlog.med.dto.dto.MedModifyDTO;
import com.bluewhale.medlog.med.model.Status;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "med")
@SQLRestriction("status = \"ACTIVE\"")
@SQLDelete(sql = "UPDATE med SET status = \"DELETED\", deleted_at = CURRENT_TIMESTAMP() where med_id = ?")
public class Med {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medId;

    @Column(name = "med_uuid")
    @Convert(converter = MedUuidConverter.class)
    private MedUuid medUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id")
    private HospitalVisitRecord hospitalVisitRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    @ToString.Exclude
    private AppUser appUser;

    private String medName;

    @Enumerated(EnumType.STRING)
    private MedType medType;

    private Float doseAmount;

    @Enumerated(EnumType.STRING)
    private DoseUnit doseUnit;

    @Column(columnDefinition = "JSON", name="dose_frequency")
    @Convert(converter = DoseFrequencyConverter.class)
    private DoseFrequency doseFrequency;

    private String instruction;

    private String effect;

    private String sideEffect;

    private LocalDate startedOn;

    private LocalDate endedOn;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE, orphanRemoval = true,
            mappedBy = "med"
    )
    @ToString.Exclude
    private List<MedIntakeRecord> medIntakeRecordList;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "med"
    )
    @ToString.Exclude
    private List<MedIntakeSnapshot> medIntakeSnapshotList;

    public static Med create(MedRegisterDTO dto, AppUser appUser, HospitalVisitRecord hospitalVisitRecord) {
        return Med.builder()
                .medId(null)
                .medUuid(new MedUuid(UUID.randomUUID().toString()))
                .hospitalVisitRecord(hospitalVisitRecord)
                .appUser(appUser)
                .medName(dto.medName())
                .medType(dto.medType())
                .doseAmount(dto.doseAmount())
                .doseUnit(dto.doseUnit())
                .doseFrequency(dto.doseFrequency())
                .instruction(dto.instruction())
                .effect(dto.effect())
                .sideEffect(dto.sideEffect())
                .startedOn(dto.startedOn())
                .endedOn(dto.endedOn())
                .status(Status.ACTIVE)
                .deletedAt(null)
                .build();
    }

    public void updateSchedule(MedModifyDTO modifyDTO) {
        this.doseFrequency = modifyDTO.doseFrequency();
        this.startedOn = modifyDTO.startedOn();
        this.endedOn = modifyDTO.endedOn();
    }
}
