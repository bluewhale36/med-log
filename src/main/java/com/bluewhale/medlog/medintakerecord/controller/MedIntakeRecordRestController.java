package com.bluewhale.medlog.medintakerecord.controller;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.application.service.MedApplicationService;
import com.bluewhale.medlog.medintakerecord.application.service.MedIntakeRecordApplicationService;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/med-intake-record")
public class MedIntakeRecordRestController {

    private final MedIntakeRecordApplicationService medIntakeRecordAppService;

    @PutMapping("/cache/{appUserUuidStr}")
    public ResponseEntity<Void> updateRecordCache(@PathVariable String appUserUuidStr) {
        medIntakeRecordAppService.putMedIntakeRecordCacheByAppUserUuid(new AppUserUuid(appUserUuidStr));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
