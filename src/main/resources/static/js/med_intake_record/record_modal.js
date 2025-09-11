document.addEventListener("DOMContentLoaded", () => {
    // --- 예정된 약 기록 관련 변수 ---
    const modal = document.getElementById("record-modal");
    const openModalButtons = document.querySelectorAll(".time-record-button");
    const closeModalBtn = modal.querySelector(".modal-close-btn");
    const modalDateLabel = modal.querySelector(".modal-date-label");
    const medTimeLabel = modal.querySelector(".med-time");
    const medCountLabel = modal.querySelector(".med-count");
    const modalMedicationItems = document.getElementById("modal-medication-items");
    const logAllTakenBtn = document.getElementById("log-all-taken-btn");
    const modalDoneBtn = document.getElementById("modal-done-btn");

    // --- 추가 복용 약 기록 관련 변수 ---
    const openAsNeededModalBtn = document.getElementById("log-as-needed-btn");
    const asNeededModal = document.getElementById("as-needed-modal");
    const closeAsNeededModalBtn = asNeededModal.querySelector(".modal-close-btn");
    const asNeededDateLabel = asNeededModal.querySelector("#as-needed-date-label");
    const asNeededMedCount = asNeededModal.querySelector("#as-needed-med-count");
    const asNeededMedList = asNeededModal.querySelector("#as-needed-med-list");
    const logAllAsNeededBtn = asNeededModal.querySelector("#log-all-as-needed-btn");
    const asNeededDoneBtn = asNeededModal.querySelector("#as-needed-done-btn");

    const pageDate = document.querySelector(".date-label").textContent.split(' ')[0];
    let medicationStatus = {};
    let asNeededMedicationStatus = {};

    /**
     * 한국 시간대(KST)를 기준으로 YYYY-MM-DDTHH:mm:ss 형식의 문자열을 생성합니다.
     */
    function toKSTISOString(date) {
        const offset = date.getTimezoneOffset() * 60000;
        const kstDate = new Date(date.getTime() - offset);
        return kstDate.toISOString().slice(0, 19);
    }

    // --- 예정된 약 기록 관련 함수 ---
    function updateLogAllButtonState() {
        const hasSkipped = Object.values(medicationStatus).some(value => value.status === 'skipped');
        logAllTakenBtn.disabled = hasSkipped;
    }

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
        if (payload.length === 0) {
            console.log("전송할 기록이 없습니다.");
            closeModal();
            return;
        }
        console.log("서버로 전송할 최종 기록:", JSON.stringify(payload));
        fetch('/med/intake/record', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
            .then(response => {
                if (response.ok) {
                    alert("기록이 성공적으로 저장되었습니다.");
                    window.location.reload();
                } else {
                    alert(`기록 저장에 실패했습니다. (상태 코드: ${response.status})`);
                }
            })
            .catch(error => {
                console.error('기록 저장 중 오류 발생:', error);
                alert("기록 저장 중 오류가 발생했습니다.");
            });
    }

    openModalButtons.forEach(button => {
        button.addEventListener("click", (e) => {
            medicationStatus = {};
            updateLogAllButtonState();

            const timeBlock = e.target.closest(".time-block");
            const time = timeBlock.dataset.stdTime;
            const medList = timeBlock.querySelector(".med-list");
            const medItems = medList.querySelectorAll("li");

            const date = new Date(pageDate.replace(/\./g, '-'));
            const options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
            modalDateLabel.textContent = date.toLocaleDateString('ko-KR', options);

            medTimeLabel.textContent = `${time}분 복용 예정 약`;
            medCountLabel.textContent = `약 개수 : ${medItems.length}개`;
            modalMedicationItems.innerHTML = '';

            medItems.forEach(item => {
                const medUuid = item.dataset.medUuid;
                const medName = item.querySelector('.med-name').textContent;
                const medDetail = item.querySelector('.med-detail').textContent;
                const medItemDiv = document.createElement('div');
                medItemDiv.classList.add('medication-item');
                medItemDiv.dataset.medUuid = medUuid;
                const stdDateTime = new Date(pageDate.replace(/\./g, '-') + 'T' + time);
                medItemDiv.dataset.stdTime = toKSTISOString(stdDateTime);
                let pillGraphicClass = 'tablet';
                medItemDiv.innerHTML = `
                    <div class="medication-item-info">
                        <div class="pill-graphic ${pillGraphicClass}"></div>
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
            addActionEventListeners();
            modal.style.display = "flex";
        });
    });

    function addActionEventListeners() {
        modalMedicationItems.querySelectorAll('.medication-actions button').forEach(button => {
            button.addEventListener('click', (e) => {
                const currentButton = e.currentTarget;
                const medItem = currentButton.closest('.medication-item');
                const medUuid = medItem.dataset.medUuid;
                const stdTime = medItem.dataset.stdTime;
                const action = currentButton.dataset.action;
                const statusTimeDiv = medItem.querySelector('.medication-status-time');
                const doseText = medItem.querySelector('.medication-dose').textContent;
                const skippedBtn = medItem.querySelector('.skipped-btn');
                const takenBtn = medItem.querySelector('.taken-btn');

                if (medicationStatus[medUuid] && medicationStatus[medUuid].status === action) {
                    delete medicationStatus[medUuid];
                    currentButton.classList.remove('active');
                    currentButton.textContent = action === 'taken' ? '복용함' : '건너뜀';
                    statusTimeDiv.textContent = '';
                } else {
                    skippedBtn.classList.remove('active');
                    takenBtn.classList.remove('active');
                    skippedBtn.textContent = '건너뜀';
                    takenBtn.textContent = '복용함';

                    currentButton.classList.add('active');
                    medicationStatus[medUuid] = { status: action, time: new Date(), stdTime: stdTime };
                    const now = new Date();
                    const currentTime = `${now.getHours()}:${String(now.getMinutes()).padStart(2, '0')}`;
                    if (action === 'taken') {
                        currentButton.textContent = '✓ 복용함';
                        statusTimeDiv.textContent = `${doseText}, ${currentTime}분`;
                        statusTimeDiv.style.color = '#0a84ff';
                    } else {
                        currentButton.textContent = '× 건너뜀';
                        statusTimeDiv.textContent = `건너뜀, ${currentTime}분`;
                        statusTimeDiv.style.color = '#ccc';
                    }
                }
                updateLogAllButtonState();
            });
        });
    }

    logAllTakenBtn.addEventListener('click', () => {
        if (logAllTakenBtn.disabled) return;
        modalMedicationItems.querySelectorAll('.medication-item').forEach(item => {
            const medUuid = item.dataset.medUuid;
            const stdTime = item.dataset.stdTime;
            medicationStatus[medUuid] = { status: 'taken', time: new Date(), stdTime: stdTime };
        });
        sendIntakeRecord();
    });

    function closeModal() { modal.style.display = "none"; }
    closeModalBtn.addEventListener("click", closeModal);
    modalDoneBtn.addEventListener("click", sendIntakeRecord);
    window.addEventListener("click", (event) => { if (event.target === modal) closeModal(); });

    // --- '추가 복용' 관련 로직 ---

    async function fetchAllMedications() {
        // TODO: 추후 실제 API 엔드포인트(/med/all 등)로 교체
        // const response = await fetch('/api/medications/all');
        // if (!response.ok) throw new Error('Failed to fetch medications');
        // return await response.json();
        console.log("Fetching all medications (mock)...");
        return [
            { medUuid: "uuid-med-1", medName: "타이레놀", doseAmount: 500, doseUnit: { title: "mg" } },
            { medUuid: "uuid-med-2", medName: "아스피린", doseAmount: 100, doseUnit: { title: "mg" } },
            { medUuid: "uuid-med-3", medName: "비타민C", doseAmount: 1000, doseUnit: { title: "mg" } }
        ];
    }

    openAsNeededModalBtn.addEventListener("click", async () => {
        try {
            const allMeds = await fetchAllMedications();
            asNeededMedicationStatus = {};
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
            addAsNeededActionEventListeners();
            asNeededModal.style.display = "flex";
        } catch (error) {
            console.error("Failed to open as-needed modal:", error);
            alert("약 목록을 불러오는 데 실패했습니다.");
        }
    });

    function addAsNeededActionEventListeners() {
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
    }

    logAllAsNeededBtn.addEventListener('click', () => {
        asNeededMedList.querySelectorAll('.medication-item').forEach(item => {
            const medUuid = item.dataset.medUuid;
            if (!asNeededMedicationStatus[medUuid]) {
                item.querySelector('.taken-btn').click();
            }
        });
    });

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

        if (payload.length === 0) {
            console.log("전송할 추가 복용 기록이 없습니다.");
            asNeededModal.style.display = 'none';
            return;
        }

        console.log("서버로 전송할 추가 복용 기록:", JSON.stringify(payload));

        /*
        // TODO: '추가 복용' 기록을 처리할 실제 엔드포인트로 교체해야 합니다.
        fetch('/med/intake/record/as-needed', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
        .then(response => {
            if (response.ok) {
                alert("추가 복용 기록이 성공적으로 저장되었습니다.");
                window.location.reload();
            } else {
                alert(`기록 저장에 실패했습니다. (상태 코드: ${response.status})`);
            }
        })
        .catch(error => {
            console.error('추가 복용 기록 저장 중 오류 발생:', error);
            alert("추가 복용 기록 저장 중 오류가 발생했습니다.");
        });
        */

        alert("추가 복용 기록이 완료되었습니다. (현재는 실제 서버로 전송되지 않습니다)");
        asNeededModal.style.display = 'none';
    }

    function closeAsNeededModal() { asNeededModal.style.display = "none"; }
    closeAsNeededModalBtn.addEventListener('click', closeAsNeededModal);
    asNeededDoneBtn.addEventListener('click', sendAsNeededRecord);
    window.addEventListener("click", (event) => { if (event.target === asNeededModal) closeAsNeededModal(); });

    // 버튼 한국어 텍스트 설정
    logAllTakenBtn.textContent = "모두 복용으로 기록";
    modalDoneBtn.textContent = "완료";
    logAllAsNeededBtn.textContent = "모두 복용으로 기록";
    asNeededDoneBtn.textContent = "완료";
});