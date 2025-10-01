package com.bluewhale.medlog.med.service;

import com.bluewhale.medlog.appuser.domain.entity.AppUser;
import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.appuser.repository.AppUserRepository;
import com.bluewhale.medlog.appuser.service.AppUserIdentifierConvertService;
import com.bluewhale.medlog.hospital.domain.entity.HospitalVisitRecord;
import com.bluewhale.medlog.hospital.repository.HospitalVisitRecordRepository;
import com.bluewhale.medlog.hospital.service.HospitalVisitRecordIdentifierConvertService;
import com.bluewhale.medlog.med.domain.entity.Med;
import com.bluewhale.medlog.med.domain.value.MedUuid;
import com.bluewhale.medlog.med.dto.dto.MedDTO;
import com.bluewhale.medlog.med.dto.dto.MedDetailViewModel;
import com.bluewhale.medlog.med.dto.dto.MedRegisterDTO;
import com.bluewhale.medlog.med.parser.MedRegisterPayloadParser;
import com.bluewhale.medlog.med.repository.MedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MedService {

    private final MedRegisterPayloadParser medRegisterPayloadParser;


    public MedRegisterDTO getMedRegisterDTOFromPayload(Map<String, Object> payload) {
        return medRegisterPayloadParser.parseData(payload);
    }

    @CachePut(key = "#entity.medUuid", value = "medDetailViewModel")
    public MedDetailViewModel cachePutMedDetailViewModel(Med entity) {
        return MedDetailViewModel.from(entity);
    }


}