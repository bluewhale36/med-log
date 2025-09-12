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

    if (frequencyType !== "AS_NEEDED" && frequencyType !== "SPECIFIC_DAYS") {
        const times = Array.from(document.querySelectorAll(".time-input-common"))
            .map(input => input.value).filter(Boolean);
        json.doseFrequencyDetail.times = times;
    }

    if (frequencyType === "CYCLICAL") {
        json.doseFrequencyDetail.onDuration = parseInt(document.getElementById("onDuration").value || 0);
        json.doseFrequencyDetail.offDuration = parseInt(document.getElementById("offDuration").value || 0);
        json.doseFrequencyDetail.cycleUnit = document.getElementById("cycleUnit").value;
    }

    if (frequencyType === "INTERVAL") {
        json.doseFrequencyDetail.interval = parseInt(document.getElementById("interval").value || 0);
    }

    if (frequencyType === "SPECIFIC_DAYS") {
        const sets = [];
        document.querySelectorAll(".day-time-set").forEach(set => {
            const selectedDays = Array.from(set.querySelectorAll(".weekday-checkbox:checked"))
                .map(cb => cb.value);
            const times = Array.from(set.querySelectorAll(".time-input"))
                .map(i => i.value).filter(Boolean);
            if (selectedDays.length && times.length) {
                sets.push({ days: selectedDays, times });
            }
        });
        json.doseFrequencyDetail.specificDays = sets;
    }

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