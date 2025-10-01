package com.bluewhale.medlog.medintakerecord.controller;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.application.service.MedApplicationService;
import com.bluewhale.medlog.medintakerecord.application.service.MedIntakeRecordApplicationService;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/med-intake-record")
@Slf4j
public class MedIntakeRecordRestController {

    private final MedIntakeRecordApplicationService medIntakeRecordAppService;

    @PutMapping("/cache/{appUserUuidStr}/{referenceDateStr}")
    public ResponseEntity<Void> updateRecordCache(
            @PathVariable String appUserUuidStr, @PathVariable String referenceDateStr
    ) {
        log.info("updateRecordCache appUserUuidStr={}", appUserUuidStr);
        medIntakeRecordAppService.putMedIntakeRecordCacheByAppUserUuid(new AppUserUuid(appUserUuidStr), LocalDate.parse(referenceDateStr));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
