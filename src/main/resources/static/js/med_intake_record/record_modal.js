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
    let medicationStatus = {}; // { medUuid: { status: 'taken' | 'skipped', time: Date } }

    function updateLogAllButtonState() {
        // medicationStatus 객체의 값(value)들 중에 status가 'skipped'인 것이 하나라도 있는지 확인
        const hasSkipped = Object.values(medicationStatus).some(value => value.status === 'skipped');
        logAllTakenBtn.disabled = hasSkipped;
    }

    // 서버로 데이터를 전송하는 함수
    function sendIntakeRecord() {
        console.log("서버로 전송할 최종 기록:", medicationStatus);
        // TODO: medicationStatus 객체를 기반으로 서버에 fetch POST 요청 구현
        // fetch('/med/intake/record', { ... body: JSON.stringify(medicationStatus) ... })
    }

    openModalButtons.forEach(button => {
        button.addEventListener("click", (e) => {
            medicationStatus = {};
            updateLogAllButtonState();

            const timeBlock = e.target.closest(".time-block");
            const time = timeBlock.querySelector(".time").textContent;
            const medList = timeBlock.querySelector(".med-list");
            const medItems = medList.querySelectorAll("li");

            const date = new Date(pageDate.replace(/\./g, '-'));
            const options = { weekday: 'long', month: 'short', day: 'numeric' };
            modalDateLabel.textContent = date.toLocaleDateString('en-US', options);
            medTimeLabel.textContent = `${time} Medications`;
            medCountLabel.textContent = `${medItems.length} Medications`;
            modalMedicationItems.innerHTML = '';

            medItems.forEach(item => {
                const medUuid = item.dataset.medUuid;
                const medName = item.querySelector('.med-name').textContent;
                const medDetail = item.querySelector('.med-detail').textContent;
                const medType = item.querySelector('.med-type').textContent;

                const medItemDiv = document.createElement('div');
                medItemDiv.classList.add('medication-item');
                medItemDiv.dataset.medUuid = medUuid;
                let pillGraphicClass = medType.toLowerCase().includes('capsule') ? 'capsule' : 'tablet';

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
                        <button class="skipped-btn" data-action="skipped">Skipped</button>
                        <button class="taken-btn" data-action="taken">Taken</button>
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
                const action = currentButton.dataset.action;

                const statusTimeDiv = medItem.querySelector('.medication-status-time');
                const doseText = medItem.querySelector('.medication-dose').textContent;
                const skippedBtn = medItem.querySelector('.skipped-btn');
                const takenBtn = medItem.querySelector('.taken-btn');

                // 이미 선택된 버튼을 다시 클릭한 경우 (취소)
                if (medicationStatus[medUuid] && medicationStatus[medUuid].status === action) {
                    delete medicationStatus[medUuid]; // 상태 제거
                    currentButton.classList.remove('active');
                    currentButton.textContent = action.charAt(0).toUpperCase() + action.slice(1); // "Taken" 또는 "Skipped"
                    statusTimeDiv.textContent = '';
                } else {
                    // 다른 버튼 상태 초기화
                    skippedBtn.classList.remove('active');
                    takenBtn.classList.remove('active');
                    skippedBtn.textContent = 'Skipped';
                    takenBtn.textContent = 'Taken';

                    // 현재 버튼 활성화
                    currentButton.classList.add('active');
                    medicationStatus[medUuid] = { status: action, time: new Date() };

                    const now = new Date();
                    const currentTime = `${now.getHours()}:${String(now.getMinutes()).padStart(2, '0')}`;

                    if (action === 'taken') {
                        currentButton.textContent = '✓ Taken';
                        statusTimeDiv.textContent = `${doseText} at ${currentTime}`;
                        statusTimeDiv.style.color = '#0a84ff';
                    } else { // skipped
                        currentButton.textContent = '× Skipped';
                        statusTimeDiv.textContent = `Skipped at ${currentTime}`;
                        statusTimeDiv.style.color = '#ccc';
                    }
                }
                updateLogAllButtonState();
            });
        });
    }

    // 'Log All as Taken' 버튼 이벤트
    logAllTakenBtn.addEventListener('click', () => {
        if (logAllTakenBtn.disabled) return;

        modalMedicationItems.querySelectorAll('.medication-item').forEach(item => {
            const medUuid = item.dataset.medUuid;
            medicationStatus[medUuid] = { status: 'taken', time: new Date() };
        });

        sendIntakeRecord();
        alert("모든 약을 복용으로 기록했습니다.");
        closeModal();
        location.reload(); // 임시 새로고침
    });

    function closeModal() {
        modal.style.display = "none";
    }

    closeModalBtn.addEventListener("click", closeModal);
    modalDoneBtn.addEventListener("click", () => {
        sendIntakeRecord();
        alert("기록이 완료되었습니다.");
        closeModal();
        location.reload(); // 임시 새로고침
    });

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            closeModal();
        }
    });
});