package com.bluewhale.medlog.med.application.usecase.med;

import com.bluewhale.medlog.common.application.usecase.IF_UseCase;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.repository.MedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArchiveMedUseCase implements IF_UseCase<MedUuid, Void> {

    private final MedRepository medRepository;

    @Override
    public Void execute(MedUuid input) {
        return null;
    }
}
