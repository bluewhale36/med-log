package com.bluewhale.medlog.security.converter;

import com.bluewhale.medlog.security.enums.IsEnabled;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IsEnabledConverter implements AttributeConverter<IsEnabled, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(IsEnabled isEnabled) {
        return isEnabled != null && isEnabled.equals(IsEnabled.ENABLED);
    }

    @Override
    public IsEnabled convertToEntityAttribute(Boolean aBoolean) {
        return aBoolean != null && aBoolean ? IsEnabled.ENABLED : IsEnabled.DISABLED;
    }
}
