package com.bluewhale.medlog.medintakesnapshot.domain.persistence;

import com.bluewhale.medlog.med.model.dosefrequency.DoseFrequencyType;
import com.bluewhale.medlog.medintakesnapshot.model.result.PolicyEvaluateTracer;
import com.bluewhale.medlog.medintakesnapshot.model.result.reason.AbstractEvaluateReason;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Converter(autoApply = true)
@RequiredArgsConstructor
public class PolicyTracerConverter implements AttributeConverter<PolicyEvaluateTracer, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(PolicyEvaluateTracer attribute) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize PolicyReason", e);
        }
        return json;
    }

    @Override
    public PolicyEvaluateTracer convertToEntityAttribute(String dbData) {
        Map<String, Object> tracerMap;
        try {
            tracerMap = objectMapper.readValue(dbData, Map.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to deserialize PolicyEvaluateTracer", e);
        }

        // JSON 데이터를 알맞는 EvaluateReason 클래스로 변환.
        // EvaluateReason 관련 데이터 추출
        Map<String, Object> reasonMap = (Map<String, Object>) tracerMap.get("reason");
        // DoseFrequencyType 추출
        String typeStr = (String) reasonMap.get("doseFrequencyType");
        DoseFrequencyType type = DoseFrequencyType.valueOf(typeStr);
        // 추출한 DoseFrequencyType 에서 EvaluateReason 클래스 가져오고 ObjectMapper 로 변환.
        AbstractEvaluateReason reason = objectMapper.convertValue(reasonMap, type.getReasonClass());

        Map<String, Object> preProcessMap = (Map<String, Object>) tracerMap.get("preProcess");
        PolicyEvaluateTracer.PreProcess preProcess = objectMapper.convertValue(preProcessMap, PolicyEvaluateTracer.PreProcess.class);

        return new PolicyEvaluateTracer(
                Boolean.parseBoolean(tracerMap.get("preProcessNeeded").toString()),
                preProcess,
                Boolean.parseBoolean(tracerMap.get("specificProcessNeeded").toString()),
                reason
        );
    }
}
