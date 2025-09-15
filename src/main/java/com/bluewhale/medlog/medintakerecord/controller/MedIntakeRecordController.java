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
import java.util.Optional;

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
    public String record(
            @RequestParam("referenceDate") @Nullable LocalDate referenceDate,
            @AuthAppUserUuid AppUserUuid appUserUuid,
            Model model
    ) {
        MedIntakeRecordDayViewDTO medIntakeRecordDayViewDTO =
                medIntakeRecordAppService.getDTOListForIntakeRecordView(appUserUuid, referenceDate)
                        .orElse(null);

        LocalDate selectedDate = (referenceDate != null) ? referenceDate : LocalDate.now();

        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("viewDTO", medIntakeRecordDayViewDTO);
        model.addAttribute("today", LocalDate.now());

        return"med_intake_record/record";
    }

    @PostMapping("/record")
    public ResponseEntity<Void> registerNewRecord(@RequestBody List<MedIntakeRecordRegisterDTO> medIntakeRecordRegisterDTOList) {
        System.out.println("\n\n\n" + medIntakeRecordRegisterDTOList + "\n\n");
        medIntakeRecordAppService.registerNewMedIntakeRecordList(medIntakeRecordRegisterDTOList);
        return ResponseEntity.ok().build();
    }
}
