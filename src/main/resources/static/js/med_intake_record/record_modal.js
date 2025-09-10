document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("record-modal");
    const openModalButtons = document.querySelectorAll(".time-record-button");
    const closeModalBtn = document.querySelector(".modal-close-btn");
    const modalDateLabel = document.querySelector(".modal-date-label");
    const medTimeLabel = document.querySelector(".med-time");
    const medCountLabel = document.querySelector(".med-count");
    const modalMedicationItems = document.getElementById("modal-medication-items");
    const logAllTakenBtn = document.getElementById("log-all-taken-btn");
    const modalDoneBtn = document.getElementById("modal-done-btn");

    const pageDate = document.querySelector(".date-label").textContent.split(' ')[0];
    let medicationStatus = {}; // { medUuid: { status: 'taken' | 'skipped', time: Date, stdTime: LocalDateTime } }

    function updateLogAllButtonState() {
        const hasSkipped = Object.values(medicationStatus).some(value => value.status === 'skipped');
        logAllTakenBtn.disabled = hasSkipped;
    }

    // 서버로 데이터를 전송하는 함수
    function sendIntakeRecord() {
        // medicationStatus 객체를 List<MedIntakeRecordRegisterDTO> 형태로 변환
        const payload = Object.keys(medicationStatus).map(medUuid => {
            const record = medicationStatus[medUuid];
            return {
                medUuid: medUuid,
                isTaken: record.status === 'taken', // 'taken'은 true, 'skipped'는 false
                estimatedDoseTime: record.stdTime, // ISO 8601 형식 (YYYY-MM-DDTHH:mm:ss)
                takenAt: record.time.toISOString() // 복용/건너뜀 버튼을 누른 시각
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
            headers: {
                'Content-Type': 'application/json',
            },
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
                medItemDiv.dataset.stdTime = stdDateTime.toISOString().slice(0, 19);

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
                    } else { // skipped
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

    function closeModal() {
        modal.style.display = "none";
    }

    closeModalBtn.addEventListener("click", closeModal);
    modalDoneBtn.addEventListener("click", () => {
        sendIntakeRecord();
    });

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            closeModal();
        }
    });

    logAllTakenBtn.textContent = "모두 복용으로 기록";
    modalDoneBtn.textContent = "완료";
});