document.addEventListener("DOMContentLoaded", () => {
    // --- 공통 변수 및 DOM 요소 ---
    const pageDateElement = document.querySelector(".date-label");
    if (!pageDateElement) return; // 필수 요소가 없으면 스크립트 실행 중단

    const pageDate = pageDateElement.textContent.split(' ')[0];
    let medicationStatus = {}; // 예정된 약 기록 상태 저장
    let asNeededMedicationStatus = {}; // 추가 복용 약 기록 상태 저장

    // --- 예정된 약 기록 모달 요소 ---
    const recordModal = document.getElementById("record-modal");
    const openModalButtons = document.querySelectorAll(".time-record-button"); // '기록' 버튼들
    const closeModalBtn = recordModal?.querySelector(".modal-close-btn");
    const modalDateLabel = recordModal?.querySelector(".modal-date-label");
    const medTimeLabel = recordModal?.querySelector(".med-time");
    const medCountLabel = recordModal?.querySelector(".med-count");
    const modalMedicationItems = document.getElementById("modal-medication-items");
    const logAllTakenBtn = document.getElementById("log-all-taken-btn");
    const modalDoneBtn = document.getElementById("modal-done-btn");

    // --- 추가 복용 약 기록 모달 요소 ---
    const openAsNeededModalBtn = document.getElementById("log-as-needed-btn");
    const asNeededModal = document.getElementById("as-needed-modal");
    const closeAsNeededModalBtn = asNeededModal?.querySelector(".modal-close-btn");
    const asNeededDateLabel = asNeededModal?.querySelector("#as-needed-date-label");
    const asNeededMedCount = asNeededModal?.querySelector("#as-needed-med-count");
    const asNeededMedList = asNeededModal?.querySelector("#as-needed-med-list");
    const logAllAsNeededBtn = asNeededModal?.querySelector("#log-all-as-needed-btn");
    const asNeededDoneBtn = asNeededModal?.querySelector("#as-needed-done-btn");

    /**
     * Date 객체를 'YYYY-MM-DDTHH:mm:ss' 형식의 KST 문자열로 변환합니다.
     */
    function toKSTISOString(date) {
        const offset = date.getTimezoneOffset() * 60000;
        const kstDate = new Date(date.getTime() - offset);
        return kstDate.toISOString().slice(0, 19);
    }

    // ===================================================================
    // --- 예정된 약 기록 관련 함수 ---
    // ===================================================================

    /**
     * '모두 복용' 버튼의 활성화/비활성화 상태를 업데이트합니다.
     */
    function updateLogAllButtonState() {
        if (!logAllTakenBtn) return;
        const hasSkipped = Object.values(medicationStatus).some(value => value.status === 'skipped');
        logAllTakenBtn.disabled = hasSkipped;
    }

    /**
     * 예정된 약 복용 기록을 서버로 전송합니다.
     */
    function sendIntakeRecord() {
        const payload = Object.keys(medicationStatus).map(medUuid => {
            const record = medicationStatus[medUuid];
            return {
                medUuid: medUuid,
                isTaken: record.status === 'taken',
                estimatedDoseTime: record.stdTime,
                takenAt: toKSTISOString(record.time)
            };
        });

        closeModal(); // 요청 전송 전에 모달을 먼저 닫습니다.

        if (payload.length === 0) {
            return; // 전송할 기록이 없으면 함수 종료
        }

        // apiFetch를 사용하여 서버에 요청
        apiFetch('/med/intake/record', {
            options: {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            },
            successMessage: "복용 기록이 저장되었습니다."
        })
            .then(response => {
                if (response && response.ok) {
                    setTimeout(() => window.location.reload(), 1500);
                }
            })
            .catch(error => {
                console.error('기록 저장 중 네트워크 오류 발생:', error);
            });
    }

    /**
     * '복용함'/'건너뜀' 버튼 클릭 시 UI와 상태를 업데이트하는 로직입니다.
     */
    function handleActionClick(e) {
        const currentButton = e.currentTarget;
        const medItem = currentButton.closest('.medication-item');
        const medUuid = medItem.dataset.medUuid;
        const stdTime = medItem.dataset.stdTime;
        const action = currentButton.dataset.action;
        const statusTimeDiv = medItem.querySelector('.medication-status-time');
        const doseText = medItem.querySelector('.medication-dose').textContent;
        const skippedBtn = medItem.querySelector('.skipped-btn');
        const takenBtn = medItem.querySelector('.taken-btn');

        // 이전에 기록된 것과 같은 버튼을 눌렀을 경우 -> 기록 취소
        if (medicationStatus[medUuid] && medicationStatus[medUuid].status === action) {
            delete medicationStatus[medUuid];
            currentButton.classList.remove('active');
            currentButton.textContent = action === 'taken' ? '복용함' : '건너뜀';
            statusTimeDiv.textContent = '';
        } else {
            // 다른 버튼들의 활성화 상태를 모두 제거
            if (skippedBtn) skippedBtn.classList.remove('active');
            if (takenBtn) takenBtn.classList.remove('active');
            if (skippedBtn) skippedBtn.textContent = '건너뜀';
            if (takenBtn) takenBtn.textContent = '복용함';

            // 현재 누른 버튼을 활성화하고 상태를 기록
            currentButton.classList.add('active');
            medicationStatus[medUuid] = { status: action, time: new Date(), stdTime: stdTime };

            const now = new Date();
            const currentTime = `${now.getHours()}:${String(now.getMinutes()).padStart(2, '0')}`;

            if (action === 'taken') {
                currentButton.textContent = '✓ 복용함';
                statusTimeDiv.textContent = `${doseText}, ${currentTime}분`;
                statusTimeDiv.style.color = '#0a84ff';
            } else { // 'skipped'
                currentButton.textContent = '× 건너뜀';
                statusTimeDiv.textContent = `건너뜀, ${currentTime}분`;
                statusTimeDiv.style.color = '#ccc';
            }
        }
        updateLogAllButtonState();
    }

    // --- 예정된 약 모달 이벤트 바인딩 ---
    openModalButtons.forEach(button => {
        // '추가 복용' 버튼은 다른 이벤트 핸들러가 있으므로 제외
        if(button.parentElement.id !== 'log-as-needed-btn') {
            button.addEventListener("click", (e) => {
                medicationStatus = {}; // 모달 열 때마다 상태 초기화
                updateLogAllButtonState();

                const timeBlock = e.target.closest(".time-block");
                if (!timeBlock) return;

                const time = timeBlock.dataset.stdTime;
                const medItems = timeBlock.querySelectorAll(".med-list li");

                const date = new Date(pageDate.replace(/\./g, '-'));
                modalDateLabel.textContent = date.toLocaleDateString('ko-KR', { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' });

                medTimeLabel.textContent = `${time} 복용 예정 약`;
                medCountLabel.textContent = `약 개수 : ${medItems.length}개`;
                modalMedicationItems.innerHTML = '';

                medItems.forEach(item => {
                    const medUuid = item.dataset.medUuid;
                    const medName = item.querySelector('.med-name')?.textContent;
                    const medDetail = item.querySelector('.med-detail')?.textContent;

                    const medItemDiv = document.createElement('div');
                    medItemDiv.className = 'medication-item';
                    medItemDiv.dataset.medUuid = medUuid;
                    const stdDateTime = new Date(`${pageDate.replace(/\./g, '-')}T${time}`);
                    medItemDiv.dataset.stdTime = toKSTISOString(stdDateTime);

                    medItemDiv.innerHTML = `
                        <div class="medication-item-info">
                            <div class="pill-graphic tablet"></div>
                            <div class="medication-details">
                                 <div class="medication-primary-info">
                                    <div class="medication-name">${medName}</div>
                                    <div class="medication-dose">${medDetail}</div>
                                </div>
                                <div class="medication-status-time"></div>
                            </div>
                        </div>
                        <div class="medication-actions">
                            <button class="skipped-btn" data-action="skipped">건너뜀</button>
                            <button class="taken-btn" data-action="taken">복용함</button>
                        </div>
                    `;
                    modalMedicationItems.appendChild(medItemDiv);
                });

                // 새로 생성된 버튼들에 이벤트 리스너 추가
                modalMedicationItems.querySelectorAll('.medication-actions button').forEach(button => {
                    button.addEventListener('click', handleActionClick);
                });

                recordModal.style.display = "flex";
            });
        }
    });

    if (logAllTakenBtn) {
        logAllTakenBtn.addEventListener('click', () => {
            if (logAllTakenBtn.disabled) return;
            modalMedicationItems.querySelectorAll('.medication-item').forEach(item => {
                const medUuid = item.dataset.medUuid;
                const stdTime = item.dataset.stdTime;
                medicationStatus[medUuid] = { status: 'taken', time: new Date(), stdTime: stdTime };
            });
            sendIntakeRecord(); // 모두 복용으로 기록 후 바로 전송
        });
    }

    function closeModal() { if(recordModal) recordModal.style.display = "none"; }
    if (closeModalBtn) closeModalBtn.addEventListener("click", closeModal);
    if (modalDoneBtn) modalDoneBtn.addEventListener("click", sendIntakeRecord);
    window.addEventListener("click", (event) => { if (event.target === recordModal) closeModal(); });


    // ===================================================================
    // --- 추가 복용 약 기록 관련 함수 ---
    // ===================================================================

    async function fetchAllMedications() {
        // TODO: 추후 실제 API 엔드포인트(/med/all 등)로 교체
        console.log("Fetching all medications (mock)...");
        return [
            { medUuid: "uuid-med-1", medName: "타이레놀", doseAmount: 500, doseUnit: { title: "mg" } },
            { medUuid: "uuid-med-2", medName: "아스피린", doseAmount: 100, doseUnit: { title: "mg" } },
            { medUuid: "uuid-med-3", medName: "비타민C", doseAmount: 1000, doseUnit: { title: "mg" } }
        ];
    }

    if (openAsNeededModalBtn) {
        openAsNeededModalBtn.addEventListener("click", async () => {
            try {
                const allMeds = await fetchAllMedications();
                asNeededMedicationStatus = {}; // 상태 초기화
                const date = new Date(pageDate.replace(/\./g, '-'));
                const options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
                asNeededDateLabel.textContent = date.toLocaleDateString('ko-KR', options);
                asNeededMedCount.textContent = `총 ${allMeds.length}개 약물 중 선택`;
                asNeededMedList.innerHTML = '';

                allMeds.forEach(med => {
                    const medItemDiv = document.createElement('div');
                    medItemDiv.classList.add('medication-item');
                    medItemDiv.dataset.medUuid = med.medUuid;
                    const medDetail = `${med.doseAmount} ${med.doseUnit.title}`;
                    medItemDiv.innerHTML = `
                        <div class="medication-item-info">
                            <div class="pill-graphic tablet"></div>
                            <div class="medication-details">
                                 <div class="medication-primary-info">
                                    <div class="medication-name">${med.medName}</div>
                                    <div class="medication-dose">${medDetail}</div>
                                </div>
                                <div class="medication-status-time"></div>
                            </div>
                        </div>
                        <div class="medication-actions">
                            <button class="taken-btn" data-action="taken">복용함</button>
                        </div>
                    `;
                    asNeededMedList.appendChild(medItemDiv);
                });

                // 추가 복용 모달 내 버튼들에 이벤트 리스너 추가
                asNeededMedList.querySelectorAll('.medication-actions button').forEach(button => {
                    button.addEventListener('click', (e) => {
                        const currentButton = e.currentTarget;
                        const medItem = currentButton.closest('.medication-item');
                        const medUuid = medItem.dataset.medUuid;
                        const statusTimeDiv = medItem.querySelector('.medication-status-time');
                        const doseText = medItem.querySelector('.medication-dose').textContent;

                        if (asNeededMedicationStatus[medUuid]) {
                            delete asNeededMedicationStatus[medUuid];
                            currentButton.classList.remove('active');
                            currentButton.textContent = '복용함';
                            statusTimeDiv.textContent = '';
                        } else {
                            const now = new Date();
                            asNeededMedicationStatus[medUuid] = { status: 'taken', time: now };
                            currentButton.classList.add('active');
                            const currentTime = `${now.getHours()}:${String(now.getMinutes()).padStart(2, '0')}`;
                            currentButton.textContent = '✓ 복용함';
                            statusTimeDiv.textContent = `${doseText}, ${currentTime}분`;
                            statusTimeDiv.style.color = '#0a84ff';
                        }
                    });
                });
                asNeededModal.style.display = "flex";
            } catch (error) {
                console.error("Failed to open as-needed modal:", error);
                alert("약 목록을 불러오는 데 실패했습니다.");
            }
        });
    }

    if (logAllAsNeededBtn) {
        logAllAsNeededBtn.addEventListener('click', () => {
            asNeededMedList.querySelectorAll('.medication-item').forEach(item => {
                const medUuid = item.dataset.medUuid;
                if (!asNeededMedicationStatus[medUuid]) {
                    item.querySelector('.taken-btn').click();
                }
            });
        });
    }

    function sendAsNeededRecord() {
        const now = new Date();
        const kstNow = toKSTISOString(now);

        const payload = Object.keys(asNeededMedicationStatus).map(medUuid => {
            const record = asNeededMedicationStatus[medUuid];
            return {
                medUuid: medUuid,
                isTaken: true,
                estimatedDoseTime: kstNow,
                takenAt: toKSTISOString(record.time)
            };
        });

        closeAsNeededModal();

        if (payload.length === 0) {
            return;
        }

        // TODO: '추가 복용' 기록을 처리할 실제 엔드포인트 구현 후 아래 주석 해제
        /*
        apiFetch('/med/intake/record/as-needed', {
            options: {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            },
            successMessage: "추가 복용 기록이 저장되었습니다."
        })
        .then(response => {
            if (response && response.ok) {
                setTimeout(() => window.location.reload(), 1500);
            }
        });
        */

        // 현재는 임시 UI 피드백만 제공
        showToast("추가 복용 기록이 임시 저장되었습니다.", 'success');
        setTimeout(() => {
            const overlay = document.getElementById('loading-overlay');
            if(overlay) overlay.style.display = 'none';
        }, 3000);
    }

    function closeAsNeededModal() { if(asNeededModal) asNeededModal.style.display = "none"; }
    if(closeAsNeededModalBtn) closeAsNeededModalBtn.addEventListener('click', closeAsNeededModal);
    if(asNeededDoneBtn) asNeededDoneBtn.addEventListener('click', sendAsNeededRecord);
    window.addEventListener("click", (event) => { if (event.target === asNeededModal) closeAsNeededModal(); });

    // 버튼 한국어 텍스트 설정
    if(logAllTakenBtn) logAllTakenBtn.textContent = "모두 복용으로 기록";
    if(modalDoneBtn) modalDoneBtn.textContent = "완료";
    if(logAllAsNeededBtn) logAllAsNeededBtn.textContent = "모두 복용으로 기록";
    if(asNeededDoneBtn) asNeededDoneBtn.textContent = "완료";
});