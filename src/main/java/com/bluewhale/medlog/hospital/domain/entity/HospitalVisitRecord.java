package com.bluewhale.medlog.hospital.domain.entity;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.hospital.domain.persistence.VisitUuidConverter;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordRegisterDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class HospitalVisitRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitId;

    @Convert(converter = VisitUuidConverter.class)
    private VisitUuid visitUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    private String hospitalName;

    private LocalDateTime consultedAt;

    private String chiefSymptom;

    private String diagnosis;

    private String physicianName;

    public static HospitalVisitRecord create(HospitalVisitRecordRegisterDTO dto, AppUser appUser) {
        return HospitalVisitRecord.builder()
                .visitId(null)
                .visitUuid(new VisitUuid(UUID.randomUUID().toString()))
                .appUser(appUser)
                .hospitalName(dto.hospitalName())
                .consultedAt(dto.consultedAt())
                .chiefSymptom(dto.chiefSymptom())
                .diagnosis(dto.diagnosis())
                .physicianName(dto.physicianName())
                .build();
    }
}
