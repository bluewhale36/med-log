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
    let medicationStatus = {};

    function updateLogAllButtonState() {
        const hasSkipped = Object.values(medicationStatus).some(status => status === 'skipped');
        logAllTakenBtn.disabled = hasSkipped;
    }

    // 서버로 데이터를 전송하는 함수
    function sendIntakeRecord() {
        // TODO: medicationStatus 객체를 기반으로 서버에 fetch 요청을 보내는 로직 구현
        // 이 함수는 'Done' 버튼 클릭 시 호출됩니다.
        console.log("서버로 전송할 최종 기록:", medicationStatus);
        // fetch('/med/intake/record', {
        //     method: 'POST',
        //     headers: { 'Content-Type': 'application/json' },
        //     body: JSON.stringify(medicationStatus)
        // })
        // .then(response => {
        //     if(response.ok) {
        //         alert("기록이 완료되었습니다.");
        //         location.reload();
        //     } else {
        //         alert("기록 저장에 실패했습니다.");
        //     }
        // });
    }

    openModalButtons.forEach(button => {
        button.addEventListener("click", (e) => {
            medicationStatus = {};
            logAllTakenBtn.disabled = false;

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
                        <button class="skipped-btn" data-action="skipped"><span class="icon"></span>Skipped</button>
                        <button class="taken-btn" data-action="taken"><span class="icon"></span>Taken</button>
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

                if (medicationStatus[medUuid] === action) {
                    delete medicationStatus[medUuid];
                    currentButton.classList.remove('active');
                    currentButton.querySelector('.icon').textContent = '';
                    statusTimeDiv.textContent = '';
                } else {
                    skippedBtn.classList.remove('active');
                    takenBtn.classList.remove('active');
                    skippedBtn.querySelector('.icon').textContent = '';
                    takenBtn.querySelector('.icon').textContent = '';

                    currentButton.classList.add('active');
                    medicationStatus[medUuid] = { status: action, time: new Date() };

                    const now = new Date();
                    const currentTime = `${now.getHours()}:${String(now.getMinutes()).padStart(2, '0')}`;

                    if (action === 'taken') {
                        currentButton.querySelector('.icon').textContent = '✓';
                        statusTimeDiv.textContent = `${doseText} at ${currentTime}`;
                        statusTimeDiv.style.color = '#0a84ff';
                    } else { // skipped
                        currentButton.querySelector('.icon').textContent = '×';
                        statusTimeDiv.textContent = `Skipped at ${currentTime}`;
                        statusTimeDiv.style.color = '#ccc';
                    }
                }
                updateLogAllButtonState();
            });
        });
    }

    // 'Log All as Taken' 버튼 이벤트: 즉시 서버 전송 로직 실행
    logAllTakenBtn.addEventListener('click', () => {
        if(logAllTakenBtn.disabled) return;

        modalMedicationItems.querySelectorAll('.medication-item').forEach(item => {
            const medUuid = item.dataset.medUuid;
            // 모든 약의 상태를 'taken'으로 설정
            medicationStatus[medUuid] = { status: 'taken', time: new Date() };
        });

        // 즉시 Done 버튼 로직 실행 (서버 전송 및 모달 닫기)
        modalDoneBtn.click();
    });

    function closeModal() {
        modal.style.display = "none";
    }

    closeModalBtn.addEventListener("click", closeModal);
    modalDoneBtn.addEventListener("click", () => {
        sendIntakeRecord();
        // 실제 서버 응답을 받은 후 처리하는 것이 이상적입니다.
        // 현재는 즉시 UI를 닫습니다.
        closeModal();
    });

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            closeModal();
        }
    });
});