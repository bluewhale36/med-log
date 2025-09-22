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

    dateScroll.addEventListener('wheel', (e) => {
        e.preventDefault();
        dateScroll.scrollBy({
            left: e.deltaY,
            behavior: 'smooth'
        });
    });

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
 * 요구사항에 따라 날짜 범위를 생성하는 함수 (수정됨)
 */
function generateDateRange(selectedDate, today) {
    const pastDays = 14; // 과거로 보여줄 날짜 수
    const futureRenderDays = 19; // 오늘 기준, 미래로 렌더링할 총 날짜 수 (14 + 5)

    // 시작일을 '선택된 날짜 - 14일'로 설정
    const startDate = new Date(selectedDate.getTime() - (pastDays * 24 * 60 * 60 * 1000));
    // 종료일을 '오늘 + 19일'로 고정
    const endDate = new Date(today.getTime() + (futureRenderDays * 24 * 60 * 60 * 1000));

    const dates = [];
    let currentDate = new Date(startDate);

    while (currentDate <= endDate) {
        dates.push(new Date(currentDate));
        currentDate.setDate(currentDate.getDate() + 1);
    }
    return dates;
}

/**
 * 각 날짜 요소를 생성하는 함수 (수정됨)
 */
function createDateItem(date, selectedDate, today) {
    const wrapper = document.createElement("div");
    wrapper.className = "date-item-wrapper";

    const dateStr = toYYYYMMDD(date);
    const selectedDateStr = toYYYYMMDD(selectedDate);
    const todayStr = toYYYYMMDD(today);

    // ▼▼▼▼▼▼▼▼▼▼ [추가된 로직] ▼▼▼▼▼▼▼▼▼▼
    const futureSelectableDays = 14;
    const maxSelectableDate = new Date(today.getTime() + (futureSelectableDays * 24 * 60 * 60 * 1000));

    // 렌더링할 날짜가 선택 가능한 최대 날짜보다 미래인 경우
    if (date > maxSelectableDate) {
        wrapper.classList.add("disabled");
    } else {
        // 선택 가능한 날짜에만 클릭 이벤트를 추가
        wrapper.setAttribute("onclick", `location.href='/med/intake/record?referenceDate=${dateStr}'`);
    }
    // ▲▲▲▲▲▲▲▲▲▲ [추가된 로직] ▲▲▲▲▲▲▲▲▲▲

    if (dateStr === selectedDateStr) {
        wrapper.classList.add("selected");
    }
    if (dateStr === todayStr) {
        wrapper.classList.add("today");
    }

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