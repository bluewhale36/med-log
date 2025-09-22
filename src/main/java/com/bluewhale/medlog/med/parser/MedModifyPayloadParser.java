package com.bluewhale.medlog.med.parser;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.dto.MedModifyDTO;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MedModifyPayloadParser implements MedPayloadParser<MedModifyDTO> {

    private final ObjectMapper objectMapper;

    @Override
    public MedModifyDTO parseData(Map<String, Object> payload) {

        DoseFrequency doseFrequency = getDoseFrequencyFromPayload(payload, objectMapper);

        String appUserUuidStr = String.valueOf(payload.get("appUserUuid"));
        String medUuidStr = String.valueOf(payload.get("medUuid"));

        if (appUserUuidStr == null || appUserUuidStr.equals("null")) {
            throw new IllegalStateException("AppUserUuid is null");
        }
        if (medUuidStr == null || medUuidStr.equals("null")) {
            throw new IllegalStateException("MedUuid is null");
        }

        AppUserUuid appUserUuid = new AppUserUuid(appUserUuidStr);
        MedUuid medUuid = new MedUuid(medUuidStr);

        String instruction = String.valueOf(payload.get("instruction"));
        String effect = String.valueOf(payload.get("effect"));
        String sideEffect = String.valueOf(payload.get("sideEffect"));

        String startedOnStr = String.valueOf(payload.get("startedOn"));
        String endedOnStr = String.valueOf(payload.get("endedOn"));

        if (startedOnStr == null ||  startedOnStr.equals("null")) {
            throw new IllegalStateException("StartedOn is null");
        }

        LocalDate startedOn = LocalDate.parse(startedOnStr);
        LocalDate endedOn = endedOnStr == null || endedOnStr.equals("null") ? null : LocalDate.parse(endedOnStr);

        return MedModifyDTO.builder()
                .appUserUuid(appUserUuid)
                .medUuid(medUuid)
                .doseFrequency(doseFrequency)
                .instruction(instruction)
                .effect(effect)
                .sideEffect(sideEffect)
                .startedOn(startedOn)
                .endedOn(endedOn)
                .build();
    }
}
