function redirectToPdf() {
    const urlParams = new URLSearchParams(window.location.search);
    const b = urlParams.has("b") ? urlParams.get("b") : "1";
    window.location.replace(window.location.host + "/AndrewSmithResume?b=" + b)
}