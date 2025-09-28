package com.bluewhale.medlog.med.controller;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.application.service.MedApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/med")
@Slf4j
public class MedRestController {

    private final MedApplicationService medApplicationService;

    @PutMapping("/cache/detail/{appUserUuidStr}")
    public ResponseEntity<Void> updateDetailCache(@PathVariable String appUserUuidStr) {
        log.info("Caught Request of appUserUuidStr : {}", appUserUuidStr);

        AppUserUuid appUserUuid = new AppUserUuid(appUserUuidStr);
        medApplicationService.putMedDetailViewModelInCache(appUserUuid);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
