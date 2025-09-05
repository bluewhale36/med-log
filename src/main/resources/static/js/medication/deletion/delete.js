document.getElementById("delete-btn").addEventListener("click", function (e) {
    e.preventDefault();

    const medUuid = document.getElementById("medUuid").value.trim();

    fetch(`/med/${medUuid}`, {
        method: "DELETE"
    }).then(res => {
        if (res.ok) {
            alert("삭제되었습니다.");
            location.href = "/med"
        }
        else alert("삭제에 실패했습니다.");
    });
});