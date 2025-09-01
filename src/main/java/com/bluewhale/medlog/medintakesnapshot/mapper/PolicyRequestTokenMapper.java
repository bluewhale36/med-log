package com.bluewhale.medlog.medintakesnapshot.mapper;

import com.bluewhale.medlog.med.dto.MedAggregationDTO;
import com.bluewhale.medlog.medintakerecord.dto.MedIntakeRecordAggregationDTO;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedIntakeToken;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestMedToken;
import com.bluewhale.medlog.medintakesnapshot.token.PolicyRequestToken;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PolicyRequestTokenMapper {

    public PolicyRequestToken getPolicyReqToken(Long appUserId, List<MedAggregationDTO> medFDTOList, LocalDate stdDate) {
        return new PolicyRequestToken(
                appUserId,
                getPolicyReqMedTokenList(medFDTOList),
                stdDate
        );
    }

    private List<PolicyRequestMedToken> getPolicyReqMedTokenList(List<MedAggregationDTO> medFDTOList) {
        return medFDTOList.stream().map(this::getPolicyReqMedToken).toList();
    }

    private PolicyRequestMedToken getPolicyReqMedToken(MedAggregationDTO medFDTO) {
        return PolicyRequestMedToken.of(
                medFDTO.getMedId(),
                medFDTO.getDoseFrequency(),
                getPolicyReqMedIntakeTokenList(medFDTO.getMirAggrDTOList()),
                medFDTO.getStartedOn(),
                medFDTO.getEndedOn()
        );
    }

    private List<PolicyRequestMedIntakeToken> getPolicyReqMedIntakeTokenList(List<MedIntakeRecordAggregationDTO> mirFDTOList) {
        if (mirFDTOList == null || mirFDTOList.isEmpty()) {
            return new ArrayList<>();
        }
        return mirFDTOList.stream().map(this::getPolicyReqMedIntakeToken).toList();
    }

    private PolicyRequestMedIntakeToken getPolicyReqMedIntakeToken(MedIntakeRecordAggregationDTO mirFDTO) {
        return new PolicyRequestMedIntakeToken(
                mirFDTO.getMedIntakeRecordId(), mirFDTO.getMedId(), mirFDTO.isTaken(), mirFDTO.getEstimatedDoseTime(), mirFDTO.getTakenAt()
        );
    }
}
