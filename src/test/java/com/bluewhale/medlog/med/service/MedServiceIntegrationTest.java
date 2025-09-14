package com.bluewhale.medlog.med.service;

import com.bluewhale.medlog.med.repository.MedRepository;
import com.bluewhale.medlog.medintakerecord.repository.MedIntakeRecordRepository;
import com.bluewhale.medlog.medintakesnapshot.repository.MedIntakeSnapshotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional // DB 상태를 격리함. 각 테스트 후 DB 롤백을 위해 사용함.
public class MedServiceIntegrationTest {

    @Autowired
    private MedService medService;

    @Autowired
    private MedRepository medRepository;

    @Autowired
    private MedIntakeRecordRepository medIntakeRecordRepository;

    @Autowired
    private MedIntakeSnapshotRepository medIntakeSnapshotRepository;
}
