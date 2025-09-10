document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("record-modal");
    const openModalButtons = document.querySelectorAll(".time-record-button");
    const closeModalBtn = document.getElementById("cancel-record-btn");
    const modalMedList = document.getElementById("modal-med-list");
    const recordForm = document.getElementById("record-form");

    openModalButtons.forEach(button => {
        button.addEventListener("click", (e) => {
            // 클릭된 버튼이 속한 time-block 찾기
            const timeBlock = e.target.closest(".time-block");
            const medList = timeBlock.querySelector(".med-list");
            const medItems = medList.querySelectorAll("li");

            // 모달의 약 목록 초기화 및 채우기
            modalMedList.innerHTML = '';
            medItems.forEach(item => {
                const medName = item.querySelector('.med-name').textContent;
                const medDetail = item.querySelector('.med-detail').textContent;

                const label = document.createElement('label');
                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.name = 'med-taken';
                checkbox.value = medName; // 실제로는 약의 고유 ID를 사용해야 합니다.

                const medInfoSpan = document.createElement('span');
                medInfoSpan.textContent = `${medName} (${medDetail})`;

                label.appendChild(checkbox);
                label.appendChild(medInfoSpan);
                modalMedList.appendChild(label);
            });

            modal.style.display = "flex";
        });
    });

    // '취소' 버튼 클릭 시 모달 닫기
    closeModalBtn.addEventListener("click", () => {
        modal.style.display = "none";
    });

    // 모달 바깥 영역 클릭 시 모달 닫기
    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });

    // 폼 제출 처리 (실제 데이터 전송 로직 필요)
    recordForm.addEventListener("submit", (e) => {
        e.preventDefault();
        const formData = new FormData(recordForm);
        const takenMeds = formData.getAll('med-taken');

        console.log("저장할 약:", takenMeds);
        alert(`${takenMeds.join(', ')} 복용 기록이 저장되었습니다.`);

        modal.style.display = "none";
        // location.reload(); // 필요 시 페이지 새로고침
    });
});