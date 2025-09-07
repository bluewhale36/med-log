package com.bluewhale.medlog.med.domain.entity;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.med.domain.persistence.DoseFrequencyConverter;
import com.bluewhale.medlog.med.domain.persistence.MedUuidConverter;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.MedRegisterDTO;
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
import org.hibernate.annotations.Where;

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
    private AppUser appUser;

    private String medName;

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
    private List<MedIntakeRecord> medIntakeRecordList;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "med"
    )
    private List<MedIntakeSnapshot> medIntakeSnapshotList;

    public static Med create(MedRegisterDTO dto, AppUser appUser, HospitalVisitRecord hospitalVisitRecord) {
        return Med.builder()
                .medId(null)
                .medUuid(new MedUuid(UUID.randomUUID().toString()))
                .hospitalVisitRecord(hospitalVisitRecord)
                .appUser(appUser)
                .medName(dto.getMedName())
                .medType(dto.getMedType())
                .doseAmount(dto.getDoseAmount())
                .doseUnit(dto.getDoseUnit())
                .doseFrequency(dto.getDoseFrequency())
                .instruction(dto.getInstruction())
                .effect(dto.getEffect())
                .sideEffect(dto.getSideEffect())
                .startedOn(dto.getStartedOn())
                .endedOn(dto.getEndedOn())
                .status(Status.ACTIVE)
                .deletedAt(null)
                .build();
    }
}
