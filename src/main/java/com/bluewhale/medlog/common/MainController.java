package com.bluewhale.medlog.common;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.application.service.HospitalVisitRecordApplicationService;
import com.bluewhale.medlog.med.application.service.MedApplicationService;
import com.bluewhale.medlog.security.annotation.AuthAppUserUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MedApplicationService medAppService;
    private final HospitalVisitRecordApplicationService hospitalVisitRecordAppService;

    @GetMapping
    @RequestMapping("/")
    public String home(@AuthAppUserUuid AppUserUuid appUserUuid, Model model) {
        if (appUserUuid != null) {
            model.addAttribute("medDTOList", medAppService.getMedDTOWrapperByAppUserUuid(appUserUuid).getMedDTOList());
            model.addAttribute("hvrDTOList", hospitalVisitRecordAppService.getHospitalVisitRecordDTOListByAppUserUuid(appUserUuid));
        }
        return "home";
    }
}
