function collectMedicationInfo() {
    const info = {
        visitUuid: document.querySelector("input[name='visitUuid']:checked")?.value || null,
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

    // EVERY_DAY, CYCLICAL, INTERVAL 타입에 대한 처리
    if (frequencyType !== "AS_NEEDED" && frequencyType !== "SPECIFIC_DAYS") {
        const doseTimeCountList = [];
        document.querySelectorAll("#time-input-container .time-row").forEach(row => {
            const time = row.querySelector(".time-input-common").value;
            const count = row.querySelector(".dose-count-input").value;
            if (time && count) {
                doseTimeCountList.push({
                    doseTime: time,
                    doseCount: parseInt(count)
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
                sets.push({ days: selectedDays, doseTimeCountList: doseTimeCountList });
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

    apiFetch("/med/new", {
        options: {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(medicationInfo)
        },
        processingMessage: "약을 등록하는 중입니다...",
        successMessage: "약이 성공적으로 등록되었습니다.",
        failureMessage: "약 등록에 실패했습니다. 다시 시도해주세요."
    })
        .then(response => {
            if (response && response.ok) {
                setTimeout(() => {
                    window.location.href = "/med";
                }, 1500);
            }
        })
        .catch(error => {
            console.error("네트워크 또는 처리 중 심각한 오류 발생:", error);
        });
});