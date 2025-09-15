document.addEventListener("DOMContentLoaded", () => {
    const dateScrollContainer = document.getElementById("date-scroll-container");
    if (!dateScrollContainer) return;

    const selectedDateStr = dateScrollContainer.dataset.selectedDate;
    const todayStr = dateScrollContainer.dataset.today;

    // Timezone 문제를 피하기 위해 new Date()에 T00:00:00를 붙이지 않습니다.
    const selectedDate = new Date(selectedDateStr);
    const today = new Date(todayStr);

    const dates = generateDateRange(selectedDate, today);

    const dateScroll = document.createElement("div");
    dateScroll.className = "date-scroll";

    dates.forEach(date => {
        const dateItem = createDateItem(date, selectedDate, today);
        dateScroll.appendChild(dateItem);
    });

    dateScrollContainer.appendChild(dateScroll);

    // ▼▼▼▼▼▼▼▼▼▼ [수정된 부분] ▼▼▼▼▼▼▼▼▼▼
    // setTimeout을 사용해 렌더링이 완료된 후 스크롤 위치를 조정합니다.
    setTimeout(() => {
        const selectedElement = dateScroll.querySelector(".selected");
        if (selectedElement) {
            const containerCenter = dateScroll.offsetWidth / 2;
            const elementCenter = selectedElement.offsetLeft + (selectedElement.offsetWidth / 2);
            const scrollLeft = elementCenter - containerCenter;

            dateScroll.scrollLeft = scrollLeft;
        }
    }, 0); // 딜레이를 0으로 주어 렌더링 사이클 직후에 실행되도록 합니다.
    // ▲▲▲▲▲▲▲▲▲▲ [수정된 부분] ▲▲▲▲▲▲▲▲▲▲
});

/**
 * 요구사항에 따라 날짜 범위를 생성하는 함수
 */
function generateDateRange(selectedDate, today) {
    let startDate, endDate;
    const fourteenDays = 14 * 24 * 60 * 60 * 1000;

    // getTime()은 UTC 기준이므로 timezone 문제 없음
    const selectedTime = selectedDate.getTime();
    const todayTime = today.getTime();

    if (selectedTime === todayTime) {
        startDate = new Date(todayTime - fourteenDays);
        endDate = new Date(todayTime + fourteenDays);
    } else if (selectedTime < todayTime) {
        startDate = new Date(selectedTime - fourteenDays);
        endDate = new Date(todayTime + fourteenDays);
    } else {
        startDate = new Date(todayTime - fourteenDays);
        endDate = new Date(selectedTime + fourteenDays);
    }

    const dates = [];
    let currentDate = new Date(startDate);
    while (currentDate <= endDate) {
        dates.push(new Date(currentDate));
        currentDate.setDate(currentDate.getDate() + 1);
    }
    return dates;
}

/**
 * 각 날짜 요소를 생성하는 함수
 */
function createDateItem(date, selectedDate, today) {
    const wrapper = document.createElement("div");
    wrapper.className = "date-item-wrapper";

    // ▼▼▼ [수정된 부분] toISOString() 대신 toYYYYMMDD 헬퍼 함수 사용 ▼▼▼
    const dateStr = toYYYYMMDD(date);
    const selectedDateStr = toYYYYMMDD(selectedDate);
    const todayStr = toYYYYMMDD(today);

    if (dateStr === selectedDateStr) {
        wrapper.classList.add("selected");
    }
    if (dateStr === todayStr) {
        wrapper.classList.add("today");
    }
    wrapper.setAttribute("onclick", `location.href='/med/intake/record?referenceDate=${dateStr}'`);
    // ▲▲▲ [수정된 부분] ▲▲▲

    const circle = document.createElement("div");
    circle.className = "date-day-circle";

    const dayLabel = document.createElement("div");
    dayLabel.className = "date-day-label";
    dayLabel.textContent = date.toLocaleDateString('ko-KR', { weekday: 'short' });

    const dateLabel = document.createElement("div");
    dateLabel.className = "date-date-label";
    dateLabel.textContent = date.getDate();

    circle.appendChild(dateLabel);
    wrapper.appendChild(circle);
    wrapper.appendChild(dayLabel);
    return wrapper;
}

/**
 * [추가] Timezone 문제를 해결하기 위한 날짜 포맷팅 헬퍼 함수
 * @param {Date} date
 * @returns {string} 'YYYY-MM-DD' 형식의 문자열
 */
function toYYYYMMDD(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // getMonth()는 0부터 시작
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}