const allWeekdays = [
    { value: "MON", label: "월" },
    { value: "TUE", label: "화" },
    { value: "WED", label: "수" },
    { value: "THU", label: "목" },
    { value: "FRI", label: "금" },
    { value: "SAT", label: "토" },
    { value: "SUN", label: "일" }
];

const usedDays = new Set();
const allDetailSections = document.querySelectorAll(".detail-section");
const timeSection = document.getElementById("time-section");
const timeInputContainer = document.getElementById("time-input-container");
const daySetContainer = document.getElementById("specific-days-container");
let setCounter = 0;

// 병원 방문 기록 선택 시 복용 시작 일자 자동 선택
document.querySelectorAll(".visit-record-radio").forEach((el) => {
    el.addEventListener("click", function(e) {
        let date = e.target.nextElementSibling.value;
        const startedOnInput = document.querySelector("#dose-start-on-input");
        startedOnInput.value = date.split("T")[0];
    });
});

// 주기 타입 선택
document.querySelectorAll(".dose-frequency-type-radio").forEach((el) => {
    el.addEventListener("change", function () {
        const selectedType = this.value;
        allDetailSections.forEach(sec => sec.style.display = 'none');
        timeInputContainer.innerHTML = '';
        addCommonTimeInput();

        if (selectedType === 'SPECIFIC_DAYS') {
            timeSection.style.display = 'none';
        } else if (selectedType !== 'AS_NEEDED') {
            timeSection.style.display = 'block';
        }

        const target = document.getElementById("detail-" + selectedType);
        if (target) target.style.display = "block";
    });
});

// 공통 시간 입력 필드 추가
function addCommonTimeInput() {
    const wrapper = document.createElement("div");
    wrapper.classList.add("time-row");

    const timeInput = document.createElement("input");
    timeInput.type = "time";
    timeInput.classList.add("time-input-common");

    const countInput = document.createElement("input");
    countInput.type = "number";
    countInput.classList.add("dose-count-input");
    countInput.min = "1";
    countInput.max = "127";
    countInput.value = "1";

    const delBtn = document.createElement("button");
    delBtn.type = "button";
    delBtn.textContent = "삭제";
    delBtn.addEventListener("click", () => {
        timeInputContainer.removeChild(wrapper);
    });

    wrapper.appendChild(timeInput);
    wrapper.appendChild(countInput);
    wrapper.appendChild(delBtn);
    timeInputContainer.appendChild(wrapper);
}

document.getElementById("add-time-btn").addEventListener("click", addCommonTimeInput);

// 세트 추가 버튼
document.getElementById("add-day-time-set-btn").addEventListener("click", () => {
    if (usedDays.size >= allWeekdays.length) return;

    const wrapper = document.createElement("div");
    wrapper.classList.add("day-time-set");
    wrapper.dataset.setId = setCounter++;

    wrapper.appendChild(document.createElement("div"));
    createWeekdayCheckboxes(wrapper);

    const timeGroup = document.createElement("div");
    timeGroup.classList.add("time-input-group");

    const baseTimeInput = document.createElement("input");
    baseTimeInput.type = "time";
    baseTimeInput.classList.add("time-input");

    const baseCountInput = document.createElement("input");
    baseCountInput.type = "number";
    baseCountInput.classList.add("dose-count-input");
    baseCountInput.min = "1";
    baseCountInput.max = "127";
    baseCountInput.value = "1";

    timeGroup.appendChild(baseTimeInput);
    timeGroup.appendChild(baseCountInput);

    const addTimeBtn = document.createElement("button");
    addTimeBtn.type = "button";
    addTimeBtn.textContent = "+ 시간 추가";
    addTimeBtn.addEventListener("click", () => {
        const newTimeInput = document.createElement("input");
        newTimeInput.type = "time";
        newTimeInput.classList.add("time-input");

        const newCountInput = document.createElement("input");
        newCountInput.type = "number";
        newCountInput.classList.add("dose-count-input");
        newCountInput.min = "1";
        newCountInput.max = "127";
        newCountInput.value = "1";

        const del = document.createElement("button");
        del.textContent = "삭제";
        del.type = "button";
        del.addEventListener("click", () => {
            timeGroup.removeChild(newTimeInput);
            timeGroup.removeChild(newCountInput);
            timeGroup.removeChild(del);
        });

        timeGroup.appendChild(document.createElement("br"));
        timeGroup.appendChild(newTimeInput);
        timeGroup.appendChild(newCountInput);
        timeGroup.appendChild(del);

        updateAddSetButtonState();
    });

    const delSetBtn = document.createElement("button");
    delSetBtn.type = "button";
    delSetBtn.textContent = "세트 삭제";
    delSetBtn.style.marginLeft = "10px";
    delSetBtn.addEventListener("click", () => {
        wrapper.remove();
        updateUsedDaysFromAllSets();
        reRenderAllCheckboxGroups();
        updateAddSetButtonState();
    });

    wrapper.appendChild(timeGroup);
    wrapper.appendChild(addTimeBtn);
    wrapper.appendChild(delSetBtn);
    daySetContainer.appendChild(wrapper);
});

// ✅ 공통 요일 체크박스 렌더링 함수
function renderWeekdayCheckboxGroup(usedSet, selectedValues = [], setId = 0) {
    const container = document.createElement("div");
    container.classList.add("weekday-checkbox-group");
    container.dataset.setId = setId;

    allWeekdays.forEach(day => {
        const label = document.createElement("label");
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.value = day.value;
        checkbox.classList.add("weekday-checkbox");
        checkbox.dataset.setId = setId;

        const isUsed = usedSet.has(day.value) && !selectedValues.includes(day.value);
        if (isUsed) {
            checkbox.disabled = true;
            label.classList.add("disabled");
        }
        if (selectedValues.includes(day.value)) {
            checkbox.checked = true;
        }

        label.appendChild(checkbox);
        label.append(` ${day.label}`);
        container.appendChild(label);
    });

    return container;
}

// 요일 그룹 생성
function createWeekdayCheckboxes(wrapper) {
    const setId = wrapper.dataset.setId;
    const newGroup = renderWeekdayCheckboxGroup(usedDays, [], setId);
    const placeholder = wrapper.querySelector(".weekday-checkbox-group") || wrapper.children[0];
    wrapper.replaceChild(newGroup, placeholder);
}

// 사용된 요일 집합 재계산
function updateUsedDaysFromAllSets() {
    usedDays.clear();
    document.querySelectorAll(".weekday-checkbox-group").forEach(group => {
        group.querySelectorAll("input[type=checkbox]:checked").forEach(cb => {
            usedDays.add(cb.value);
        });
    });
}

// 요일 그룹 전체 다시 그리기
function reRenderAllCheckboxGroups() {
    document.querySelectorAll(".day-time-set").forEach(wrapper => {
        const oldGroup = wrapper.querySelector(".weekday-checkbox-group");
        const selected = oldGroup
            ? Array.from(oldGroup.querySelectorAll("input[type=checkbox]:checked")).map(cb => cb.value)
            : [];
        if (oldGroup) wrapper.removeChild(oldGroup);

        const setId = wrapper.dataset.setId;
        const newGroup = renderWeekdayCheckboxGroup(usedDays, selected, setId);
        wrapper.insertBefore(newGroup, wrapper.firstChild);
    });
}

// 체크 시 usedDays 갱신 및 렌더링
document.addEventListener("change", function (e) {
    if (e.target.matches(".weekday-checkbox")) {
        updateUsedDaysFromAllSets();
        reRenderAllCheckboxGroups();
        updateAddSetButtonState();
    }
});

function updateAddSetButtonState() {
    const btn = document.getElementById("add-day-time-set-btn");
    if (!btn) return;
    btn.disabled = usedDays.size >= allWeekdays.length;
}