
var btnBack = document.querySelector("#btn-back");

function goBack() {
    window.history.back();
}

btnBack.addEventListener('click', goBack);