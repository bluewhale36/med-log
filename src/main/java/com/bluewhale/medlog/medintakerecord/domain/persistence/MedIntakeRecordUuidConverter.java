package com.bluewhale.medlog.medintakerecord.domain.persistence;

import com.bluewhale.medlog.medintakerecord.domain.value.MedIntakeRecordUuid;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MedIntakeRecordUuidConverter implements AttributeConverter<MedIntakeRecordUuid, String> {
    @Override
    public String convertToDatabaseColumn(MedIntakeRecordUuid attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public MedIntakeRecordUuid convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.isBlank() ? new MedIntakeRecordUuid(dbData) : null;
    }
}
