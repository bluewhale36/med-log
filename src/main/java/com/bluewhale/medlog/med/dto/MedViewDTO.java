package com.bluewhale.medlog.med.dto;

import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class MedViewDTO {

    private final MedDTO medDTO;
    private final String simpleMedDoseDescription;
    private final String detailedMedDoseDescription;

    public static MedViewDTO from(MedDTO medDTO) {
        if (medDTO == null) {
            throw new IllegalArgumentException("MedDTO cannot be null");
        }
        String simpleDescription = "";
        String detailedDescription = "";

        return MedViewDTO.builder()
                .medDTO(medDTO)
                .simpleMedDoseDescription(simpleDescription)
                .detailedMedDoseDescription(detailedDescription)
                .build();
    }
}
