package com.bluewhale.medlog.security.converter;

import com.bluewhale.medlog.security.enums.IsLocked;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IsLockedConverter implements AttributeConverter<IsLocked, Boolean> {
    @Override
    public Boolean convertToDatabaseColumn(IsLocked isLocked) {
        return isLocked != null && isLocked.equals(IsLocked.LOCKED);
    }

    @Override
    public IsLocked convertToEntityAttribute(Boolean aBoolean) {
        return aBoolean != null && aBoolean ? IsLocked.LOCKED : IsLocked.UNLOCKED;
    }
}
