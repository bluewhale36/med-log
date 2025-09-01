package com.bluewhale.medlog.hospital.domain.persistence;

import com.bluewhale.medlog.hospital.domain.value.VisitUuid;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class VisitUuidConverter implements AttributeConverter<VisitUuid, String> {
    @Override
    public String convertToDatabaseColumn(VisitUuid attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public VisitUuid convertToEntityAttribute(String dbData) {
        return dbData == null || dbData.isBlank() ? null : new VisitUuid(dbData);
    }
}
