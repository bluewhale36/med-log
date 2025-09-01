package com.bluewhale.medlog.med.domain.persistence;

import com.bluewhale.medlog.med.domain.value.MedUuid;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MedUuidConverter implements AttributeConverter<MedUuid, String> {

    @Override
    public String convertToDatabaseColumn(MedUuid attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public MedUuid convertToEntityAttribute(String dbData) {
        return dbData == null || dbData.isBlank() ? null : new MedUuid(dbData);
    }
}
