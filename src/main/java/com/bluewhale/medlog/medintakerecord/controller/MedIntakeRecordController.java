package com.bluewhale.medlog.medintakerecord.controller;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.application.service.MedApplicationService;
import com.bluewhale.medlog.medintakerecord.application.service.MedIntakeRecordApplicationService;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordDayViewDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordRegisterDTO;
import com.bluewhale.medlog.security.annotation.AuthAppUserUuid;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/med/intake")
@RequiredArgsConstructor
public class MedIntakeRecordController {

    private final MedApplicationService medAppService;
    private final MedIntakeRecordApplicationService medIntakeRecordAppService;

    @GetMapping({"", "/"})
    public String index(@AuthAppUserUuid AppUserUuid appUserUuid, Model model) {
        model.addAttribute("medDTOList", medAppService.getMedDTOListByAppUserUuid(appUserUuid));
        return "med_intake_record/main";
    }

    @GetMapping("/record")
    public String record(@RequestParam("date") @Nullable LocalDate date,
                         @AuthAppUserUuid AppUserUuid appUserUuid,
                         Model model) {
        List<MedIntakeRecordDayViewDTO> list = medIntakeRecordAppService.getDTOListForIntakeRecordView(appUserUuid);
        List<LocalDate> dateList = list.stream().map(MedIntakeRecordDayViewDTO::getStdDate).toList();

        model.addAttribute("selectedDate", date == null ? LocalDate.now() : date);
        model.addAttribute("dateList", dateList);
        model.addAttribute("mirdvDTOList", list);

        return"med_intake_record/record";
    }

    @PostMapping("/record")
    public ResponseEntity<Void> registerNewRecord(@RequestBody List<MedIntakeRecordRegisterDTO> medIntakeRecordRegisterDTOList) {
        System.out.println(medIntakeRecordRegisterDTOList);
        return ResponseEntity.ok().build();
    }
}
