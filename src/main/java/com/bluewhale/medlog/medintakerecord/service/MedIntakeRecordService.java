package com.bluewhale.medlog.medintakerecord.service;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MedIntakeRecordService {

    private final MedIntakeRecordRepository medIntakeRecordRepository;

    @CachePut(key = "#appUserUuid.asString().concat(':').concat(#referenceDate.toString())", value = "recordDayViewDTO")
    public MedIntakeRecordDayViewDTO updateRecordDayViewCache(AppUserUuid appUserUuid, LocalDate referenceDate) {

        return null;
    }
}
