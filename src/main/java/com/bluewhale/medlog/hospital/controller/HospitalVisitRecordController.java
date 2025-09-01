package com.bluewhale.medlog.hospital.controller;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDTO;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordRegisterDTO;
import com.bluewhale.medlog.hospital.service.HospitalVisitRecordService;
import com.bluewhale.medlog.security.annotation.AuthAppUserUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hospital/visit")
public class HospitalVisitRecordController {

    private final HospitalVisitRecordService hvrServ;

    @GetMapping({"", "/"})
    public String hospitalVisitRecord(@AuthAppUserUuid AppUserUuid appUserUuid, Model model) {
        model.addAttribute("hvrDTOList", hvrServ.getHospitalVisitRecordListByAppUserUuid(appUserUuid));
        return "hospital/visit/main";
    }

    @GetMapping("/{visitUuid}")
    public String getOneHospitalVisitRecord(@PathVariable("visitUuid") String visitUuid, Model model) {
        model.addAttribute("hvrDTO", hvrServ.getOneHospitalVisitRecordByVisitUuid(new VisitUuid(visitUuid)));
        return "hospital/visit/one";
    }

    @GetMapping("/register")
    public String hospitalVisitRegister(@AuthAppUserUuid AppUserUuid appUserUuid, Model model) {
        model.addAttribute("appUserUuid", appUserUuid);
        return "hospital/visit/register";
    }

    @PostMapping("/register")
    public String registerNewHospitalVisitLog(@ModelAttribute HospitalVisitRecordRegisterDTO dto) {
        hvrServ.registerNewHospitalVisitRecord(dto);
        return "redirect:/hospital/visit";
    }
}
