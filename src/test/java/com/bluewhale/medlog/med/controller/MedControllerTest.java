package com.bluewhale.medlog.med.controller;

import com.bluewhale.medlog.hospital.application.service.HospitalVisitRecordApplicationService;
import com.bluewhale.medlog.med.application.service.MedApplicationService;
import com.bluewhale.medlog.med.service.MedService;
import com.bluewhale.medlog.security.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MedController.class)
@Import(SecurityConfig.class)
@ExtendWith(MockitoExtension.class)
public class MedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MedApplicationService medAppServ;

    @MockBean
    private MedService medServ;

    @MockBean
    private HospitalVisitRecordApplicationService hospitalVisitRecordAppService;

    @Test
    @WithMockUser
    @DisplayName("Med Modification Request API Test")
    void medModification() throws Exception {

//        String uuidString = UUID.randomUUID().toString();
//
//        AppUserUuid appUserUuid = new AppUserUuid(uuidString);
//        MedUuid medUuid = new MedUuid(uuidString);
//
//        DoseFrequency frq = DoseFrequency.of(DoseFrequencyType.AS_NEEDED, new AsNeededDetail());
//
//        LocalDate start = LocalDate.now();
//        LocalDate end = start.plusDays(1);
//
//        MedTimeModifyDTO modifyDTO = new MedTimeModifyDTO(
//                appUserUuid, medUuid, frq, start, end
//        );
//
//        System.out.println(medUuid.asString());
//        System.out.println(modifyDTO.getMedUuid().asString());
//
//        mockMvc
//                .perform(
//                        patch("/med/{medUuid}", medUuid.asString())
//                                .with(csrf())
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(modifyDTO))
//                );
//
//        verify(medAppServ, times(1)).modifyMedTimeSchedule(any(MedTimeModifyDTO.class));
    }

}
