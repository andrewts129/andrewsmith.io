setTimeout(function () {
    var urlParams = new URLSearchParams(window.location.search);
    var b = urlParams.has("b") ? urlParams.get("b") : "1";
    window.location.replace("http://www.andrewsmith.io/AndrewSmithResume?b=" + b)
}, 250);