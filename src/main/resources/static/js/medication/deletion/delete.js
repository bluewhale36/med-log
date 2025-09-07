document.addEventListener("DOMContentLoaded", () => {
    // CSRF 토큰 정보 가져오기
    // const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    // const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    // 모달 및 버튼 요소 가져오기
    const deleteModal = document.getElementById("delete-modal");
    const openModalBtn = document.getElementById("delete-btn");
    const closeModalBtn = document.getElementById("cancel-delete-btn");
    const confirmDeleteBtn = document.getElementById("confirm-delete-btn");

    // '삭제' 버튼 클릭 시 모달 열기
    openModalBtn.addEventListener("click", () => {
        deleteModal.style.display = "flex";
    });

    // '취소' 버튼 클릭 시 모달 닫기
    closeModalBtn.addEventListener("click", () => {
        deleteModal.style.display = "none";
    });

    // 모달 바깥 영역 클릭 시 모달 닫기
    window.addEventListener("click", (event) => {
        if (event.target === deleteModal) {
            deleteModal.style.display = "none";
        }
    });

    // 모달의 '삭제' 버튼 클릭 시 삭제 로직 실행
    confirmDeleteBtn.addEventListener("click", () => {
        // data-med-uuid 속성에서 약 UUID 가져오기
        const medUuid = document.getElementById("medUuid").value;
        if (!medUuid) {
            alert("삭제할 약의 정보를 찾을 수 없습니다.");
            return;
        }

        // fetch API를 사용하여 DELETE 요청 보내기
        fetch(`/med/${medUuid}`, {
            method: "DELETE",
            // headers: {
            //     // CSRF 토큰을 헤더에 추가
            //     [csrfHeader]: csrfToken
            // },
        })
            .then(response => {
                if (response.ok) {
                    // 삭제 성공 시
                    alert("약 정보가 성공적으로 삭제되었습니다.");
                    // 약 목록 페이지로 리디렉션
                    window.location.href = "/med";
                } else {
                    // 삭제 실패 시
                    alert(`삭제에 실패했습니다. (상태: ${response.status})`);
                    // 모달 닫기
                    deleteModal.style.display = "none";
                }
            })
            .catch(error => {
                console.error("삭제 중 오류 발생:", error);
                alert("삭제 요청 중 오류가 발생했습니다.");
                deleteModal.style.display = "none";
            });
    });
});