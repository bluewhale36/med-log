document.addEventListener("DOMContentLoaded", () => {
    const dateScrollContainer = document.getElementById("date-scroll-container");
    if (!dateScrollContainer) return;

    const selectedDateStr = dateScrollContainer.dataset.selectedDate;
    const todayStr = dateScrollContainer.dataset.today;

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

    // ▼▼▼▼▼▼▼▼▼▼ [추가된 부분] ▼▼▼▼▼▼▼▼▼▼
    // 마우스 휠로 가로 스크롤을 제어하는 이벤트 리스너
    dateScroll.addEventListener('wheel', (e) => {
        // 페이지의 기본 세로 스크롤 동작을 막음
        e.preventDefault();

        // 마우스 휠의 세로 움직임(e.deltaY)만큼 가로로 스크롤(left)
        // 주어진 값(e.deltaY)만큼 부드럽게 스크롤
        dateScroll.scrollBy({
            left: e.deltaY,
            behavior: 'smooth'
        });
    });
    // ▲▲▲▲▲▲▲▲▲▲ [추가된 부분] ▲▲▲▲▲▲▲▲▲▲

    setTimeout(() => {
        const selectedElement = dateScroll.querySelector(".selected");
        if (selectedElement) {
            const containerCenter = dateScroll.offsetWidth / 2;
            const elementCenter = selectedElement.offsetLeft + (selectedElement.offsetWidth / 2);
            const scrollLeft = elementCenter - containerCenter;

            dateScroll.scrollLeft = scrollLeft;
        }
    }, 0);
});

/**
 * 요구사항에 따라 날짜 범위를 생성하는 함수
 */
function generateDateRange(selectedDate, today) {
    const fourteenDays = 14 * 24 * 60 * 60 * 1000;

    const endDate = new Date(today.getTime() + fourteenDays);
    const startDate = new Date(selectedDate.getTime() - fourteenDays);

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

    const circle = document.createElement("div");
    circle.className = "date-day-circle";

    const dayLabel = document.createElement("div");
    dayLabel.className = "date-day-label";
    dayLabel.textContent = date.toLocaleString('ko-KR', { weekday: 'short' });

    const dateLabel = document.createElement("div");
    dateLabel.className = "date-date-label";
    dateLabel.textContent = date.getDate();

    circle.appendChild(dateLabel);
    wrapper.appendChild(circle);
    wrapper.appendChild(dayLabel);
    return wrapper;
}

/**
 * Timezone 문제를 해결하기 위한 날짜 포맷팅 헬퍼 함수
 */
function toYYYYMMDD(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}