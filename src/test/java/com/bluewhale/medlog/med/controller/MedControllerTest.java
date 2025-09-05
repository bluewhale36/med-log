package com.bluewhale.medlog.med.controller;

import com.bluewhale.medlog.hospital.service.HospitalVisitRecordService;
import com.bluewhale.medlog.med.application.service.MedApplicationService;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.service.MedService;
import com.bluewhale.medlog.security.config.SecurityConfig;
import com.bluewhale.medlog.security.service.UserDetailsService_Impl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedController.class)
@Import(SecurityConfig.class)
@ExtendWith(MockitoExtension.class)
public class MedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedApplicationService medAppServ;

    @MockBean
    private MedService medServ;

    @MockBean
    private HospitalVisitRecordService hvrServ;

    @MockBean
    private UserDetailsService_Impl userDetailsService;


    @Test
    @WithMockUser
    @DisplayName("Med Deletion Request API Test")
    void medDeletion() throws Exception {
        String uuidString = UUID.randomUUID().toString();

        mockMvc
                .perform(delete("/med/{medUuid}", uuidString).with(csrf()))
                .andExpect(status().isOk());

        verify(medAppServ, times(1)).deleteMedWithRelatedInfo(any(MedUuid.class));
    }
}
