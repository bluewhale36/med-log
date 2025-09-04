package com.bluewhale.medlog.med.domain.entity;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.med.domain.persistence.DoseFrequencyConverter;
import com.bluewhale.medlog.med.domain.persistence.MedUuidConverter;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.bluewhale.medlog.medintakerecord.domain.entity.MedIntakeRecord;
import com.bluewhale.medlog.medintakesnapshot.domain.entity.MedIntakeSnapshot;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "med")
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
}
