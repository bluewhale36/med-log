package com.bluewhale.medlog.medintakesnapshot.controller;

import com.bluewhale.medlog.medintakesnapshot.service.MedIntakeSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/med/intake/snapshot")
@RequiredArgsConstructor
public class MedIntakeSnapshotController {

    private final MedIntakeSnapshotService medIntakeSnapshotService;
}
