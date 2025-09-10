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
    let medicationStatus = {}; // 약품 상태 추적 객체 (key: medUuid, value: 'taken' or 'skipped')

    // 'Log All as Taken' 버튼 상태 업데이트 함수
    function updateLogAllButtonState() {
        const hasSkipped = Object.values(medicationStatus).some(status => status === 'skipped');
        logAllTakenBtn.disabled = hasSkipped;
    }

    // 모달 열기 버튼 이벤트
    openModalButtons.forEach(button => {
        button.addEventListener("click", (e) => {
            medicationStatus = {}; // 모달 열 때 상태 초기화
            logAllTakenBtn.disabled = false; // 버튼 활성화

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
                            <div class="medication-name">${medName}</div>
                            <div class="medication-dose">${medDetail}</div>
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

    // 각 약품의 버튼에 이벤트 리스너 추가
    function addActionEventListeners() {
        modalMedicationItems.querySelectorAll('.medication-actions button').forEach(button => {
            button.addEventListener('click', (e) => {
                const medItem = e.target.closest('.medication-item');
                const medUuid = medItem.dataset.medUuid;
                const action = e.target.dataset.action; // 'taken' or 'skipped'

                const statusTimeDiv = medItem.querySelector('.medication-status-time');
                const doseText = medItem.querySelector('.medication-dose').textContent;
                const skippedBtn = medItem.querySelector('.skipped-btn');
                const takenBtn = medItem.querySelector('.taken-btn');

                // 현재 클릭된 상태와 같으면 선택 해제 (초기 상태로)
                if (medicationStatus[medUuid] === action) {
                    delete medicationStatus[medUuid];
                    e.target.classList.remove('active');
                    statusTimeDiv.textContent = '';
                } else {
                    // 다른 버튼의 active 클래스 제거
                    skippedBtn.classList.remove('active');
                    takenBtn.classList.remove('active');

                    // 현재 버튼에 active 클래스 추가
                    e.target.classList.add('active');
                    medicationStatus[medUuid] = action;

                    const now = new Date();
                    const currentTime = `${now.getHours()}:${String(now.getMinutes()).padStart(2, '0')}`;

                    if (action === 'taken') {
                        statusTimeDiv.textContent = `${doseText} at ${currentTime}`;
                        statusTimeDiv.style.color = '#0a84ff';
                        console.log(`Med UUID: ${medUuid}, Status: Taken at ${currentTime}`);
                    } else { // skipped
                        statusTimeDiv.textContent = `Skipped at ${currentTime}`;
                        statusTimeDiv.style.color = '#ccc';
                        console.log(`Med UUID: ${medUuid}, Status: Skipped at ${currentTime}`);
                    }
                }

                // 'Log All' 버튼 상태 업데이트
                updateLogAllButtonState();
            });
        });
    }

    // 'Log All as Taken' 버튼 이벤트
    logAllTakenBtn.addEventListener('click', () => {
        if(logAllTakenBtn.disabled) return;

        modalMedicationItems.querySelectorAll('.medication-item').forEach(item => {
            const medUuid = item.dataset.medUuid;
            // 이미 taken이거나 skipped가 아닌 경우에만 taken으로 처리
            if (medicationStatus[medUuid] !== 'taken' && medicationStatus[medUuid] !== 'skipped') {
                item.querySelector('.taken-btn').click();
            }
        });
    });

    function closeModal() {
        modal.style.display = "none";
    }

    closeModalBtn.addEventListener("click", closeModal);
    modalDoneBtn.addEventListener("click", () => {
        // TODO: 서버에 최종 기록(medicationStatus 객체)을 전송하는 로직
        console.log("최종 기록:", medicationStatus);
        alert("기록이 완료되었습니다.");
        closeModal();
        location.reload(); // 임시로 새로고침
    });

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            closeModal();
        }
    });
});