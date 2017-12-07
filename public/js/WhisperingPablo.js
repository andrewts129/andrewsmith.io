window.onload = function() {
    updateWhisperCount();
    setInterval(updateWhisperCount, 3000);
};

function updateWhisperCount() {
    var url = "https://whispering-pablo.herokuapp.com/api/numsubmissions";

    window.fetch(url, {
        method: "get"
    }).then(function (response) {
        return response.json();
    }).then(function (json) {
        document.getElementById("num-whispers").innerText = json.number;
        document.getElementById("output-label").innerText = "The last message submitted was: ";
    });
}

function submitMessage() {
    var inputTextField = document.getElementById("input-text-field");
    var input = inputTextField.value;
    inputTextField.value = "";

    var url = "https://whispering-pablo.herokuapp.com/api/postmessage?request=" + input;

    window.fetch(url, {
        method: "post"
    }).then(function (response) {
        return response.json();
    }).then(function (json) {
        document.getElementById("output").innerText = json.response.message;
    });

    updateWhisperCount();
}