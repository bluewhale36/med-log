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

    // 모달 열기 버튼 이벤트
    openModalButtons.forEach(button => {
        button.addEventListener("click", (e) => {
            const timeBlock = e.target.closest(".time-block");
            const time = timeBlock.querySelector(".time").textContent;
            const medList = timeBlock.querySelector(".med-list");
            const medItems = medList.querySelectorAll("li");

            // 모달 헤더 정보 설정
            const date = new Date(pageDate.replace(/\./g, '-'));
            const options = { weekday: 'long', month: 'short', day: 'numeric', year: 'numeric' };
            modalDateLabel.textContent = date.toLocaleDateString('ko-KR', options);

            medTimeLabel.textContent = `${time}분 복용 예정 약`;
            medCountLabel.textContent = `약 개수 : ${medItems.length}개`;

            modalMedicationItems.innerHTML = ''; // 기존 약 목록 초기화

            medItems.forEach(item => {
                const medUuid = item.dataset.medUuid;
                const medName = item.querySelector('.med-name').textContent;
                const medDetail = item.querySelector('.med-detail').textContent;
                const medType = item.querySelector('.med-type').textContent;

                const medItemDiv = document.createElement('div');
                medItemDiv.classList.add('medication-item');
                medItemDiv.dataset.medUuid = medUuid;

                let pillGraphicClass = '';
                if (medType.toLowerCase().includes('capsule')) {
                    pillGraphicClass = 'capsule';
                } else {
                    pillGraphicClass = 'tablet';
                }

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
                        <button class="skipped-btn">Skipped</button>
                        <button class="taken-btn">Taken</button>
                    </div>
                `;
                modalMedicationItems.appendChild(medItemDiv);
            });

            addActionEventListeners();
            modal.style.display = "flex";
        });
    });

    // 각 약품의 Skipped, Taken 버튼에 이벤트 리스너 추가
    function addActionEventListeners() {
        modalMedicationItems.querySelectorAll('.medication-item').forEach(medItem => {
            const medUuid = medItem.dataset.medUuid;
            const statusTimeDiv = medItem.querySelector('.medication-status-time');
            const doseText = medItem.querySelector('.medication-dose').textContent;

            const skippedBtn = medItem.querySelector('.skipped-btn');
            const takenBtn = medItem.querySelector('.taken-btn');

            skippedBtn.addEventListener('click', () => {
                const now = new Date();
                const currentTime = `${now.getHours()}:${String(now.getMinutes()).padStart(2, '0')}`;
                statusTimeDiv.textContent = `Skipped at ${currentTime}`;
                console.log(`Med UUID: ${medUuid}, Status: Skipped at ${currentTime}`);
                // TODO: 서버로 '건너뜀' 상태 전송 로직
            });

            takenBtn.addEventListener('click', () => {
                const now = new Date();
                const currentTime = `${now.getHours()}:${String(now.getMinutes()).padStart(2, '0')}`;
                statusTimeDiv.textContent = `${doseText} at ${currentTime}`;
                console.log(`Med UUID: ${medUuid}, Status: Taken at ${currentTime}`);
                // TODO: 서버로 '복용함' 상태 전송 로직
            });
        });
    }

    // 'Log All as Taken' 버튼 이벤트
    logAllTakenBtn.addEventListener('click', () => {
        modalMedicationItems.querySelectorAll('.taken-btn').forEach(btn => btn.click());
    });

    // 모달 닫기 기능
    function closeModal() {
        modal.style.display = "none";
    }

    closeModalBtn.addEventListener("click", closeModal);
    modalDoneBtn.addEventListener("click", () => {
        // TODO: 서버에 최종 기록을 보낸 후, 성공 응답을 받으면 페이지를 새로고침하거나 UI를 업데이트
        alert("기록이 완료되었습니다.");
        closeModal();
        location.reload(); // 임시로 새로고침
    });

    // 모달 바깥 영역 클릭 시 닫기
    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            closeModal();
        }
    });
});