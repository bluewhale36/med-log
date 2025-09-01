package com.bluewhale.medlog.med.parser;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.med.dto.ParsedMedRegisterDTO;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.AbstractDoseFrequencyDetail;
import com.bluewhale.medlog.med.model.medication.DoseUnit;
import com.bluewhale.medlog.med.model.medication.MedType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PayloadParser {

    private final ObjectMapper objectMapper;

    public ParsedMedRegisterDTO parseData(Map<String, Object> payload) {

        // 1. doseFrequency 분해 및 조립
        Map<String, Object> freqMap = (Map<String, Object>) payload.get("doseFrequency");
        String typeStr = (String) freqMap.get("doseFrequencyType");
        Map<String, Object> detailMap = (Map<String, Object>) freqMap.get("doseFrequencyDetail");

        DoseFrequencyType type = DoseFrequencyType.valueOf(typeStr);
        AbstractDoseFrequencyDetail detail = objectMapper.convertValue(detailMap, type.getDetailClass());
        DoseFrequency doseFrequency = DoseFrequency.of(type, detail);

        String visitUuidStr = parseString(payload.get("visitUuid"));

        ParsedMedRegisterDTO dto = ParsedMedRegisterDTO.builder()
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

    private Long parseLong(Object obj) {
        if (obj == null) return null;

        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }

        if (obj instanceof String && !((String) obj).isBlank()) {
            return Long.parseLong((String) obj);
        }

        return null;
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
