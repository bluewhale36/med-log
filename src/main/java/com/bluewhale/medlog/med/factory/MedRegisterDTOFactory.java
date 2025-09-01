package com.bluewhale.medlog.med.factory;

import com.bluewhale.medlog.med.dto.MedRegisterDTO;
import com.bluewhale.medlog.med.dto.ParsedMedRegisterDTO;
import com.bluewhale.medlog.med.parser.PayloadParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MedRegisterDTOFactory {

    private final PayloadParser payloadParser;

    public MedRegisterDTO fromPayload(Map<String, Object> payload) {
        ParsedMedRegisterDTO parsedDTO = payloadParser.parseData(payload);

        return new MedRegisterDTO(
                parsedDTO.getVisitUuid(),
                parsedDTO.getAppUserUuid(),
                parsedDTO.getMedName(),
                parsedDTO.getMedType(),
                parsedDTO.getDoseAmount(),
                parsedDTO.getDoseUnit(),
                parsedDTO.getDoseFrequency(),
                parsedDTO.getInstruction(),
                parsedDTO.getEffect(),
                parsedDTO.getSideEffect(),
                parsedDTO.getStartedOn(),
                parsedDTO.getEndedOn()
        );
    }
}