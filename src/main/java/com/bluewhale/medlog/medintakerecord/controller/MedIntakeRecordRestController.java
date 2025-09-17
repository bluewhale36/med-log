package com.bluewhale.medlog.medintakerecord.controller;

import com.bluewhale.medlog.med.application.service.MedApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MedIntakeRecordRestController {

    private final MedApplicationService medAppService;
}
