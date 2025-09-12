const overlay = document.getElementById('loading-overlay');
const toast = document.getElementById('toast-notification');
const toastIcon = document.getElementById('toast-icon');
const toastMessage = document.getElementById('toast-message');
let toastTimeout;

// 아이콘 SVG 정의
const icons = {
    loading: '<div class="spinner"></div>',
    success: `<svg fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="3"><path stroke-linecap="round" stroke-linejoin="round" d="M5 13l4 4L19 7" /></svg>`,
    error: `<svg fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="3"><path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" /></svg>`
};

/**
 * 상단 알림(토스트)를 표시하는 함수
 * @param {string} message - 표시할 메시지
 * @param {'loading'|'success'|'error'} state - 알림 상태
 * @param {number} duration - 성공/실패 시 표시될 시간 (ms)
 */
function showToast(message, state, duration = 3000) {
    clearTimeout(toastTimeout); // 이전 타이머 제거

    toast.className = 'toast'; // 클래스 초기화
    toast.classList.add(state, 'show');
    toastIcon.innerHTML = icons[state];
    toastMessage.textContent = message;

    // 로딩 상태가 아닐 때만 자동으로 사라지도록 타이머 설정
    if (state !== 'loading') {
        toastTimeout = setTimeout(() => {
            toast.classList.remove('show');
            if (overlay) overlay.style.display = 'none'; // 오버레이도 함께 숨김
        }, duration);
    }
}

/**
 * 전역 fetch 래퍼 함수
 * @param {string} url - 요청 URL
 * @param {object} config - 설정 객체
 * @param {RequestInit} config.options - fetch에 전달할 옵션 (method, body 등)
 * @param {string} [config.processingMessage] - 요청 처리 중 표시할 메시지
 * @param {string} [config.successMessage] - 성공 시 표시할 메시지
 * @param {string} [config.failureMessage] - 실패 시 표시할 메시지
 * @returns {Promise<Response>}
 */
async function apiFetch(url, { options, processingMessage, successMessage, failureMessage } = {}) {
    if (overlay) overlay.style.display = 'block';

    showToast(processingMessage || '처리 중입니다...', 'loading');

    try {
        const response = await fetch(url, options);
        if (response.ok) {
            showToast(successMessage || '요청이 성공하였습니다.', 'success');
        } else {
            // 서버가 에러 응답을 보냈을 때
            const errorData = await response.text(); // 더 자세한 에러를 위해 text()나 json() 사용 가능
            console.error('Server Error:', response.status, errorData);
            showToast(failureMessage || '요청 처리 중 오류가 발생했습니다.', 'error');
        }
        return response;
    } catch (error) {
        // 네트워크 에러 등 fetch 자체가 실패했을 때
        console.error('Fetch Error:', error);
        showToast(failureMessage || '요청에 실패하였습니다.', 'error');
        throw error;
    }
}