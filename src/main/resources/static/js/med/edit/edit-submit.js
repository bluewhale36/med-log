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

    // new/validation.js와 동일한 로직으로 상세 정보 수집
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

    console.log(JSON.stringify(medicationInfo));

    // 'fetch' 대신 'fetchWithLoading' 함수를 사용합니다.
    fetchWithLoading(`/med/${medUuid}`, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(medicationInfo)
    }).then(response => {
        if (response.ok) {
            alert("수정 성공!");
            window.location.reload(); // 성공 시 페이지 새로고침
        } else {
            alert(`수정 실패 (상태 코드: ${response.status})`);
        }
    }).catch(error => {
        console.error("수정 중 오류 발생:", error);
        alert("수정 요청 중 오류가 발생했습니다.");
    });
    // .finally() 블록은 fetchWithLoading 함수에 이미 구현되어 있으므로 필요 없습니다.
});