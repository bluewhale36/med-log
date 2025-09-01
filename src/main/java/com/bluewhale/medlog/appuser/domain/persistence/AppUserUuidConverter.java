package com.bluewhale.medlog.appuser.domain.persistence;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AppUserUuidConverter implements AttributeConverter<AppUserUuid, String> {

    @Override
    public String convertToDatabaseColumn(AppUserUuid attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public AppUserUuid convertToEntityAttribute(String dbData) {
        return dbData == null || dbData.isBlank() ? null : new AppUserUuid(dbData);
    }
}
