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

    // 기존 fetch 대신 fetchWithLoading 함수 사용
    fetchWithLoading("/med/new", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(medicationInfo)
    })
        .then(res => {
            if (res.ok) {
                alert("제출 성공");
                window.location.href = "/med"; // 성공 시 페이지 이동
            } else {
                alert("제출 실패");
            }
        })
        .catch(error => {
            // fetchWithLoading에서 에러를 다시 던져주므로 여기서 잡을 수 있습니다.
            alert("제출 요청 중 오류가 발생했습니다.");
        });
    // .finally() 블록은 fetchWithLoading 함수 내부에 있으므로 여기서 필요 없습니다.
});