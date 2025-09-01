    document.addEventListener("DOMContentLoaded", () => {
    const body = document.body;
    const btn = document.getElementById("theme-toggle-btn");

    // 초기값: prefers-color-scheme 또는 localStorage
    const savedTheme = localStorage.getItem("theme");
    const systemPrefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;

    if (savedTheme) {
    body.classList.add(savedTheme);
} else {
    body.classList.add(systemPrefersDark ? "dark-theme" : "light-theme");
}

    updateButtonText();

    btn.addEventListener("click", () => {
    if (body.classList.contains("light-theme")) {
    body.classList.replace("light-theme", "dark-theme");
    localStorage.setItem("theme", "dark-theme");
} else {
    body.classList.replace("dark-theme", "light-theme");
    localStorage.setItem("theme", "light-theme");
}
    updateButtonText();
});

    function updateButtonText() {
    btn.textContent = body.classList.contains("dark-theme") ? "☀️ 라이트 모드" : "🌙 다크 모드";
}
});