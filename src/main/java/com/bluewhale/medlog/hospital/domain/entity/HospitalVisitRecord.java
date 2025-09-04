package com.bluewhale.medlog.hospital.domain.entity;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.hospital.domain.persistence.VisitUuidConverter;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
