package com.bluewhale.medlog.med.parser;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.AbstractDoseFrequencyDetail;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public interface MedPayloadParser<R> {

    default DoseFrequency getDoseFrequencyFromPayload(
            Map<String, Object> payload, ObjectMapper objectMapper
    ) {
        Map<String, Object> frequencyMap;
        String typeStr;
        Map<String, Object> detailMap;

        List<Object> timeCountList;

        try {
            frequencyMap = (Map<String, Object>) payload.get("doseFrequency");
            typeStr = (String) frequencyMap.get("doseFrequencyType");
            detailMap = (Map<String, Object>) frequencyMap.get("doseFrequencyDetail");
        } catch (ClassCastException e) {
            throw new IllegalStateException(e);
        }

        DoseFrequencyType type = DoseFrequencyType.valueOf(typeStr);

        if (!type.equals(DoseFrequencyType.AS_NEEDED)) {
            try {
                timeCountList = (List<Object>) detailMap.get("doseTimeCountList");
                if (timeCountList != null && !timeCountList.isEmpty()) {
                    detailMap.put("doseTimeCountList", timeCountList);
                }
            } catch (ClassCastException e) {
                throw new IllegalStateException(e);
            }
        }

        AbstractDoseFrequencyDetail detail = objectMapper.convertValue(detailMap, type.getDetailClass());

        return DoseFrequency.of(type, detail);
    }

    R parseData(Map<String, Object> payload);
}
