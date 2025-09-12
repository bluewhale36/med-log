function collectMedicationInfo() {
    const info = {
        visitUuid: document.querySelector("input[name='visitUuid']")?.value || null,
        appUserUuid: document.querySelector("input[name='appUserUuid']")?.value || null,
        medName: document.querySelector("input[name='medName']")?.value || "",
        medType: document.querySelector("select[name='medType']")?.value || null,
        doseAmount: document.querySelector("input[name='doseAmount']")?.value || null,
        doseUnit: document.querySelector("select[name='doseUnit']")?.value || null,
        doseFrequency: "",
        instruction: document.querySelector("textarea[name='instruction']")?.value || null,
        effect: document.querySelector("input[name='effect']")?.value || null,
        sideEffect: document.querySelector("input[name='sideEffect']")?.value || null,
        startedOn: document.querySelector("input[name='startedOn']")?.value || null,
        endedOn: document.querySelector("input[name='endedOn']")?.value || null
    };

    const frequencyType = document.querySelector("input[name='frequencyType']:checked")?.value;
    if (!frequencyType) return info;

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


document.getElementById("medication-form").addEventListener("submit", function (e) {
    e.preventDefault();

    const medicationInfo = collectMedicationInfo();
    if (!medicationInfo) return;

    // apiFetch 함수로 교체
    apiFetch("/med/new", {
        options: {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(medicationInfo)
        },
        successMessage: "약이 성공적으로 등록되었습니다."
    })
        .then(response => {
            if (response && response.ok) {
                // 성공 메시지를 사용자가 볼 수 있도록 1.5초 후 페이지 이동
                setTimeout(() => {
                    window.location.href = "/med";
                }, 1500);
            }
            // 실패 시에는 apiFetch가 자동으로 에러 메시지를 표시
        })
        .catch(error => {
            // 네트워크 에러 등 fetch 자체가 실패한 경우, apiFetch 내부에서 처리하므로 추가 작업 불필요
            console.error("네트워크 또는 처리 중 심각한 오류 발생:", error);
        });
});