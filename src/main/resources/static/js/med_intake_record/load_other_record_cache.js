document.addEventListener("DOMContentLoaded", function() {
    const appUserUuid = document.querySelector("#app-user-uuid").value;
    const url = `/api/med-intake-record/cache/${appUserUuid}`;
    try {
        const result = fetch(url, {method: "PUT"});
    } catch (e) {
        console.error(e);
    }
})