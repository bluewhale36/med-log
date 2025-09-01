document.addEventListener("DOMContentLoaded", function () {
    const editNotify = document.getElementById("edit-notify");
    const editBtn = document.getElementById("edit-toggle-btn");
    const cancelBtn = document.getElementById("cancel-edit-btn");
    const form = document.getElementById("medication-edit-form");
    const inputs = form.querySelectorAll("input, textarea");
    const editModeSection = form.querySelector(".edit-mode-buttons");

    editBtn.addEventListener("click", () => {
        inputs.forEach(el => {
            if (!el.hasAttribute("readonly")) el.disabled = false;
        });
        editNotify.style.display = "block";
        editModeSection.style.display = "flex";
        editBtn.style.display = "none";
    });

    cancelBtn.addEventListener("click", () => {
        // 페이지 리로드로 복원
        window.location.reload();
    });
});