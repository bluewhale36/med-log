package com.bluewhale.medlog.med.domain.persistence;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequency;
import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.med.model.dosefrequency.detail.AbstractDoseFrequencyDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Converter(autoApply = true)
@RequiredArgsConstructor
public class DoseFrequencyConverter implements AttributeConverter<DoseFrequency, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(DoseFrequency attribute) {
        String frequencyJson;
        try {
            frequencyJson = objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize DoseFrequency", e);
        }
        return frequencyJson;
    }

    @Override
    public DoseFrequency convertToEntityAttribute(String dbData) {
        Map<String, Object> freqMap;
        try {
            freqMap = objectMapper.readValue(dbData, Map.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to deserialize DoseFrequency", e);
        }

        String typeStr = (String) freqMap.get("doseFrequencyType");
        Map<String, Object> detailMap = (Map<String, Object>) freqMap.get("doseFrequencyDetail");

        DoseFrequencyType type = DoseFrequencyType.valueOf(typeStr);
        AbstractDoseFrequencyDetail detail = objectMapper.convertValue(detailMap, type.getDetailClass());

        return DoseFrequency.of(type, detail);
    }
}
