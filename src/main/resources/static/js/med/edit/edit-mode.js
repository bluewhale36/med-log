document.addEventListener("DOMContentLoaded", function () {
    const editNotify = document.getElementById("edit-notify");
    const editBtn = document.getElementById("edit-toggle-btn");
    const cancelBtn = document.getElementById("cancel-edit-btn");
    const form = document.getElementById("medication-edit-form");

    const displayModeSection = document.getElementById("display-mode-form");
    const editModeFormSection = document.getElementById("edit-mode-form");

    const inputs = form.querySelectorAll("input[name='startedOn'], input[name='endedOn'], textarea, input[name='effect'], input[name='sideEffect']");
    const editModeButtons = form.querySelector(".edit-mode-buttons");
    const persistentButtons = form.querySelector(".persistent-buttons");

    editBtn.addEventListener("click", () => {
        // 수정 가능한 필드 활성화
        inputs.forEach(el => el.disabled = false);

        // UI 전환
        editNotify.style.display = "block";
        displayModeSection.style.display = "none";
        editModeFormSection.style.display = "block";
        editModeButtons.style.display = "flex";
        persistentButtons.style.display = "none";
        editBtn.style.display = "none";

        // 현재 선택된 복용 주기에 맞춰 동적 폼 초기화
        document.querySelector("input[name='frequencyType']:checked").dispatchEvent(new Event('change'));

        // ✅ 기존 데이터로 폼 채우기
        populateEditForm(doseFrequencyData);
    });

    cancelBtn.addEventListener("click", () => {
        // 간단하게 페이지를 새로고침하여 원래 상태로 복원
        window.location.reload();
    });

    /**
     * doseFrequencyData 객체를 기반으로 수정 폼의 값을 채우는 함수
     * @param data {object} - Thymeleaf에서 전달받은 doseFrequency 객체
     */
    function populateEditForm(data) {
        if (!data) return;

        const detail = data.doseFrequencyDetail;
        const type = data.doseFrequencyType;

        // ▼▼▼▼▼▼▼▼▼▼ 변경된 부분 시작 ▼▼▼▼▼▼▼▼▼▼

        // 공통 시간 채우기 (EVERY_DAY, CYCLICAL, INTERVAL)
        // 'times' 대신 'doseTimeCountList'를 확인하도록 변경
        if (detail.doseTimeCountList && Array.isArray(detail.doseTimeCountList)) {
            const timeContainer = document.getElementById("time-input-container");
            timeContainer.innerHTML = ''; // 기존 필드 초기화
            detail.doseTimeCountList.forEach(dtc => {
                addCommonTimeInput(); // validation.js의 함수 재사용
                const lastRow = timeContainer.querySelector(".time-row:last-child");
                if (lastRow) {
                    // 새로 생성된 시간 및 개수 입력 필드에 값을 설정
                    lastRow.querySelector(".time-input-common").value = dtc.doseTime;
                    lastRow.querySelector(".dose-count-input").value = dtc.doseCount;
                }
            });
        }

        switch (type) {
            case 'CYCLICAL':
                document.getElementById('onDuration').value = detail.onDuration || '';
                document.getElementById('offDuration').value = detail.offDuration || '';
                document.getElementById('cycleUnit').value = detail.cycleUnit || 'DAY';
                break;
            case 'INTERVAL':
                document.getElementById('interval').value = detail.interval || '';
                break;
            case 'SPECIFIC_DAYS':
                const addSetBtn = document.getElementById("add-day-time-set-btn");
                const specificDaysContainer = document.getElementById("specific-days-container");
                specificDaysContainer.innerHTML = ''; // 기존 세트 초기화

                if (detail.specificDays && Array.isArray(detail.specificDays)) {
                    detail.specificDays.forEach(set => {
                        addSetBtn.click(); // validation.js의 로직을 트리거하여 새 세트 생성
                        const newSetWrapper = specificDaysContainer.querySelector(".day-time-set:last-child");

                        // 요일 체크
                        if (newSetWrapper && set.days && Array.isArray(set.days)) {
                            set.days.forEach(day => {
                                const checkbox = newSetWrapper.querySelector(`input.weekday-checkbox[value="${day}"]`);
                                if (checkbox) {
                                    checkbox.checked = true;
                                }
                            });
                        }

                        // 시간 및 개수 입력
                        if (newSetWrapper && set.doseTimeCountList && Array.isArray(set.doseTimeCountList)) {
                            const timeGroup = newSetWrapper.querySelector(".time-input-group");
                            timeGroup.innerHTML = ''; // 기본 시간 입력 필드 제거

                            set.doseTimeCountList.forEach(dtc => {
                                // 새로운 시간-개수 입력 세트 생성
                                const timeInput = document.createElement('input');
                                timeInput.type = 'time';
                                timeInput.className = 'time-input';
                                timeInput.value = dtc.doseTime;

                                const countInput = document.createElement('input');
                                countInput.type = 'number';
                                countInput.className = 'dose-count-input';
                                countInput.min = '1';
                                countInput.max = '127';
                                countInput.value = dtc.doseCount;

                                timeGroup.appendChild(timeInput);
                                timeGroup.appendChild(countInput);
                                timeGroup.appendChild(document.createElement('br'));
                            });
                        }
                    });
                    // 모든 세트가 채워진 후, 전체 체크박스 상태 업데이트
                    updateUsedDaysFromAllSets();
                    reRenderAllCheckboxGroups();
                }
                break;
            case 'EVERY_DAY':
            case 'AS_NEEDED':
            default:
                // 별도 처리 없음
                break;
        }
        // ▲▲▲▲▲▲▲▲▲▲ 변경된 부분 끝 ▲▲▲▲▲▲▲▲▲▲
    }
});