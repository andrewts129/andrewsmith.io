function submitMessage() {
    var inputTextField = document.getElementById("inputText");
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

}
