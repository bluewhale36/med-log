package com.bluewhale.medlog.med.service;

import com.bluewhale.medlog.med.application.service.MedApplicationService;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.repository.MedRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


@SpringBootTest
public class MedApplicationServiceTest {

    @Autowired
    private MedApplicationService medApplicationService;

    @MockBean
    private MedRepository medRepository;

    @Test
    @DisplayName("Delete Med With Related Info")
    void softDeleteMedWithMedUuid() {
        MedUuid medUuid = new MedUuid(UUID.randomUUID().toString());
        Long medId = 10L;

        given(medRepository.findIdByMedUuid(medUuid)).willReturn(Optional.of(medId));

        medApplicationService.softDeleteMedWithMedUuid(medUuid);

        then(medRepository).should(times(1)).deleteById(medId);
    }
}
