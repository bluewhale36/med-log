// 로딩 인디케이터 DOM 요소를 가져옵니다.
const loadingIndicator = document.getElementById('loading-indicator');

// 로딩 UI를 보여주는 함수
const showLoading = () => {
    if (loadingIndicator) loadingIndicator.style.display = 'flex';
};

// 로딩 UI를 숨기는 함수
const hideLoading = () => {
    if (loadingIndicator) loadingIndicator.style.display = 'none';
};

/**
 * 전역 fetch 래퍼 함수
 * @param {string} url - 요청을 보낼 URL
 * @param {object} options - fetch에 전달할 옵션 (method, headers, body 등)
 * @returns {Promise<Response>} - fetch의 반환값인 Promise
 */
const fetchWithLoading = async (url, options) => {
    showLoading(); // 요청 시작 시 로딩 UI 표시
    try {
        // 실제 fetch 요청 실행
        const response = await fetch(url, options);
        return response;
    } catch (error) {
        // 에러 발생 시 콘솔에 로그를 남기고 에러를 다시 던져서
        // 호출한 쪽의 .catch() 블록에서 처리할 수 있도록 함
        console.error('Fetch Error:', error);
        throw error;
    } finally {
        hideLoading(); // 성공/실패 여부와 관계없이 요청 완료 시 로딩 UI 숨김
    }
};