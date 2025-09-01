package com.bluewhale.medlog.medintakesnapshot.token;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
@Builder
public class PolicyRequestToken {

    private final Long appUserId;
    private final List<PolicyRequestMedToken> policyReqMedTokenList;
    private final LocalDate stdDate;
}
