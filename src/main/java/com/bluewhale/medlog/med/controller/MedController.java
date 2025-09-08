package com.bluewhale.medlog.med.controller;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.application.service.HospitalVisitRecordApplicationService;
import com.bluewhale.medlog.hospital.dto.HospitalVisitRecordDTO;
import com.bluewhale.medlog.med.application.service.MedApplicationService;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedForm;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.bluewhale.medlog.med.service.MedService;
import com.bluewhale.medlog.security.annotation.AuthAppUserUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/med")
@RequiredArgsConstructor
public class MedController {

    private final MedApplicationService medAppService;
    private final HospitalVisitRecordApplicationService hospitalVisitRecordAppService;
    private final MedService medService;

    @GetMapping({"", "/"})
    public String home(@AuthAppUserUuid AppUserUuid appUserUuid, Model model) {
        model.addAttribute("medDTOList", medService.getMedDTOListByAppUserUuid(appUserUuid));
        return "med/main";
    }

    @GetMapping("/{medUuid}")
    public String getOneMedInfo(@PathVariable("medUuid") String medUuid, Model model) {
        model.addAttribute("medDTO", medService.getMedDTOByMedUuid(new MedUuid(medUuid)));
        return "med/one-and-edit";
    }

    @GetMapping("/new")
    public String registerNewMedication(@AuthAppUserUuid AppUserUuid uuid, Model model) {
        List<HospitalVisitRecordDTO> hvrDTOList = hospitalVisitRecordAppService.getHospitalVisitRecordDTOListByAppUserUuid(uuid);

        model.addAttribute("appUserUuid", uuid.asString());
        model.addAttribute("medFormList", MedForm.values());
        model.addAttribute("medTypeList", MedType.values());
        model.addAttribute("frequencyTypeList", DoseFrequencyType.values());
        model.addAttribute("doseUnitList", DoseUnit.values());
        model.addAttribute("hvrDTOList", hvrDTOList);
        return "med/new";
    }

    @PostMapping("/new")
    @ResponseBody
    public String getFormData(@RequestBody Map<String, Object> payload) {
        medAppService.registerNewMed(payload);
        return null;
    }

    @DeleteMapping("/{medUuid}")
    public ResponseEntity<Void> deleteMed(@PathVariable("medUuid") String medUuid) {
        medAppService.softDeleteMedWithMedUuid(new MedUuid(medUuid));
        return ResponseEntity.ok().build();
    }
}
