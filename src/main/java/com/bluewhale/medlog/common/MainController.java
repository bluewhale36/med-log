package com.bluewhale.medlog.common;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.service.HospitalVisitRecordService;
import com.bluewhale.medlog.med.service.MedService;
import com.bluewhale.medlog.medintakesnapshot.service.MedIntakeSnapshotService;
import com.bluewhale.medlog.security.annotation.AuthAppUserUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MedService medServ;
    private final HospitalVisitRecordService hvrServ;
    private final MedIntakeSnapshotService misServ;

    @GetMapping
    @RequestMapping("/")
    public String home(@AuthAppUserUuid AppUserUuid appUserUuid, Model model) {
        if (appUserUuid != null) {
            model.addAttribute("medDTOList", medServ.getMedDTOListByAppUserUuid(appUserUuid));
            model.addAttribute("hvrDTOList", hvrServ.getHospitalVisitRecordListByAppUserUuid(appUserUuid));
        }
        return "home";
    }
}
