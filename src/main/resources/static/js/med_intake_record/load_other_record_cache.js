document.addEventListener("DOMContentLoaded", function() {
    const appUserUuid = document.querySelector("#app-user-uuid")?.value;
    const selectedDate = document.querySelector("#selected-date")?.value;

    const url = `/api/med-intake-record/cache/${appUserUuid}/${selectedDate}`;
    try {
        const result = fetch(url, {method: "PUT"});
    } catch (e) {
        console.error(e);
    }
});