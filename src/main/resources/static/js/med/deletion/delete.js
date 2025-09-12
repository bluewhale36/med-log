document.addEventListener("DOMContentLoaded", () => {
    const deleteModal = document.getElementById("delete-modal");
    const openModalBtn = document.getElementById("delete-btn");
    const closeModalBtn = document.getElementById("cancel-delete-btn");
    const confirmDeleteBtn = document.getElementById("confirm-delete-btn");

    if (!deleteModal || !openModalBtn || !closeModalBtn || !confirmDeleteBtn) return;

    openModalBtn.addEventListener("click", () => {
        deleteModal.style.display = "flex";
    });

    closeModalBtn.addEventListener("click", () => {
        deleteModal.style.display = "none";
    });

    window.addEventListener("click", (event) => {
        if (event.target === deleteModal) {
            deleteModal.style.display = "none";
        }
    });

    confirmDeleteBtn.addEventListener("click", () => {
        const medUuid = document.getElementById("medUuid").value;
        if (!medUuid) {
            alert("삭제할 약의 정보를 찾을 수 없습니다.");
            return;
        }

        // 모달 닫기
        deleteModal.style.display = "none";

        // apiFetch 함수로 교체
        apiFetch(`/med/${medUuid}`, {
            options: {
                method: "DELETE",
            },
            processingMessage: "약 정보를 삭제하는 중입니다...",
            successMessage: "약 정보가 성공적으로 삭제되었습니다.",
            failureMessage: "삭제에 실패했습니다. 다시 시도해주세요."
        }).then(response => {
            if (response && response.ok) {
                setTimeout(() => {
                    window.location.href = "/med";
                }, 1500);
            }
        }).catch(error => {
            console.error("네트워크 또는 처리 중 심각한 오류 발생:", error);
        });
    });
});