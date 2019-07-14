function redirectToPdf() {
    const urlParams = new URLSearchParams(window.location.search);
    const b = urlParams.has("b") ? urlParams.get("b") : "0";
    window.location.replace("http://" + window.location.host + "/AndrewSmithResume?b=" + b)
}