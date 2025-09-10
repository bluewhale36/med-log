package com.bluewhale.medlog.med.parser;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.med.dto.MedRegisterDTO;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MedRegisterPayloadParser implements MedPayloadParser<MedRegisterDTO> {

    private final ObjectMapper objectMapper;

    public MedRegisterDTO parseData(Map<String, Object> payload) {

        DoseFrequency doseFrequency = getDoseFrequencyFromPayload(payload, objectMapper);

        String visitUuidStr = parseString(payload.get("visitUuid"));

        MedRegisterDTO dto = MedRegisterDTO.builder()
                .visitUuid(visitUuidStr != null ? new VisitUuid(visitUuidStr) : null)
                .appUserUuid(new AppUserUuid(parseString(payload.get("appUserUuid"))))
                .medName(parseString(payload.get("medName")))
                .medType(MedType.valueOf(parseString(payload.get("medType"))))
                .doseAmount(Float.valueOf(parseString(payload.get("doseAmount"))))
                .doseUnit(DoseUnit.valueOf(parseString(payload.get("doseUnit"))))
                .doseFrequency(doseFrequency)
                .instruction(parseString(payload.get("instruction")))
                .effect(parseString(payload.get("effect")))
                .sideEffect(parseString(payload.get("sideEffect")))
                .startedOn(parseDate(payload.get("startedOn")))
                .endedOn(parseDate(payload.get("endedOn")))
                .build();
        System.out.println(dto);
        return dto;
    }

    private LocalDate parseDate(Object obj) {
        if (obj == null || obj.toString().isBlank()) return null;
        return LocalDate.parse(obj.toString());
    }

    private String parseString(Object obj) {
        if (obj == null || obj.toString().isBlank()) return null;
        return obj.toString();
    }
}
