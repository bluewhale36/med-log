function collectMedicationInfoForUpdate() {
    const info = {
        appUserUuid: document.getElementById("appUserUuid")?.value || null,
        medUuid: document.getElementById("medUuid")?.value || null,
        doseFrequency: "",
        instruction: document.querySelector("textarea[name='instruction']")?.value || null,
        effect: document.querySelector("input[name='effect']")?.value || null,
        sideEffect: document.querySelector("input[name='sideEffect']")?.value || null,
        startedOn: document.querySelector("input[name='startedOn']")?.value || null,
        endedOn: document.querySelector("input[name='endedOn']")?.value || null
    };

    const frequencyType = document.querySelector("input[name='frequencyType']:checked")?.value;
    if (!frequencyType) {
        alert("복용 주기를 선택해주세요.");
        return null;
    }

    const json = { doseFrequencyType: frequencyType, doseFrequencyDetail: {} };

    // ▼▼▼▼▼▼▼▼▼▼ 변경된 부분 시작 ▼▼▼▼▼▼▼▼▼▼

    // EVERY_DAY, CYCLICAL, INTERVAL 타입에 대한 처리
    if (frequencyType !== "AS_NEEDED" && frequencyType !== "SPECIFIC_DAYS") {
        const doseTimeCountList = [];
        // '.time-row' 단위로 순회하며 시간과 개수를 한 쌍으로 읽어옴
        document.querySelectorAll("#time-input-container .time-row").forEach(row => {
            const time = row.querySelector(".time-input-common").value;
            const count = row.querySelector(".dose-count-input").value;
            if (time && count) {
                doseTimeCountList.push({
                    doseTime: time,
                    doseCount: parseInt(count) // 입력값을 정수로 변환
                });
            }
        });
        json.doseFrequencyDetail.doseTimeCountList = doseTimeCountList;
    }

    if (frequencyType === "CYCLICAL") {
        json.doseFrequencyDetail.onDuration = parseInt(document.getElementById("onDuration").value || 0);
        json.doseFrequencyDetail.offDuration = parseInt(document.getElementById("offDuration").value || 0);
        json.doseFrequencyDetail.cycleUnit = document.getElementById("cycleUnit").value;
    }

    if (frequencyType === "INTERVAL") {
        json.doseFrequencyDetail.interval = parseInt(document.getElementById("interval").value || 0);
    }

    // SPECIFIC_DAYS 타입에 대한 처리
    if (frequencyType === "SPECIFIC_DAYS") {
        const sets = [];
        document.querySelectorAll(".day-time-set").forEach(set => {
            const selectedDays = Array.from(set.querySelectorAll(".weekday-checkbox:checked"))
                .map(cb => cb.value);

            const doseTimeCountList = [];
            // '.time-input'과 '.dose-count-input'을 각각 찾아 순서대로 매칭
            const timeInputs = set.querySelectorAll(".time-input-group .time-input");
            const countInputs = set.querySelectorAll(".time-input-group .dose-count-input");

            timeInputs.forEach((timeInput, index) => {
                const time = timeInput.value;
                const count = countInputs[index].value;
                if (time && count) {
                    doseTimeCountList.push({
                        doseTime: time,
                        doseCount: parseInt(count)
                    });
                }
            });

            if (selectedDays.length > 0 && doseTimeCountList.length > 0) {
                sets.push({ dayList: selectedDays, doseTimeCountList: doseTimeCountList });
            }
        });
        json.doseFrequencyDetail.specificDays = sets;
    }

    // ▲▲▲▲▲▲▲▲▲▲ 변경된 부분 끝 ▲▲▲▲▲▲▲▲▲▲

    info.doseFrequency = json;
    return info;
}

document.getElementById("medication-edit-form").addEventListener("submit", function (e) {
    e.preventDefault();

    const medicationInfo = collectMedicationInfoForUpdate();
    if (!medicationInfo) return;

    const medUuid = medicationInfo.medUuid;

    // apiFetch 함수로 교체
    apiFetch(`/med/${medUuid}`, {
        options: {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(medicationInfo)
        },
        processingMessage: "약 정보를 수정하는 중입니다...",
        successMessage: "약 정보가 성공적으로 수정되었습니다.",
        failureMessage: "수정에 실패했습니다. 다시 시도해주세요."
    }).then(response => {
        if (response && response.ok) {
            // 성공 메시지를 사용자가 볼 수 있도록 1.5초 후 페이지 새로고침
            setTimeout(() => {
                window.location.reload();
            }, 1500);
        }
    }).catch(error => {
        console.error("네트워크 또는 처리 중 심각한 오류 발생:", error);
    });
});