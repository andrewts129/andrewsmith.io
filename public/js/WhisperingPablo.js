window.onload = function() {
    // The intervals are chosen so that they will rarely occur at the same time. This probably does not matter at all
    updateWhisperCount();
    setInterval(updateWhisperCount, 2237);

    updateFeed();
    setInterval(updateFeed, 1179);
};

function updateWhisperCount() {
    var url = "https://whispering-pablo.herokuapp.com/api/get/numsubmissions";

    window.fetch(url, {
        method: "get"
    }).then(function (response) {
        return response.json();
    }).then(function (json) {
        document.getElementById("num-whispers").innerText = json.number;
        document.getElementById("connecting-label").innerText = "";
    });

}

function submitMessage() {
    var inputTextField = document.getElementById("input-text-field");
    var input = inputTextField.value;

    if (isValidSubmission(input)) {
        inputTextField.value = "";

        var url = "https://whispering-pablo.herokuapp.com/api/post/submit?message=" + input;

        window.fetch(url, {
            method: "post"
        }).then(function (response) {
            return response.json();
        }).then(function (json) {
            document.getElementById("output").innerText = json.response.message;
        });
    }

    updateWhisperCount();
    updateFeed();
}

function updateFeed() {
    function renderFeed(feedJson) {
        var feedHolder = document.getElementById("feed");

        // Clears children
        // https://stackoverflow.com/questions/3955229/remove-all-child-elements-of-a-dom-node-in-javascript
        while (feedHolder.lastChild) {
            feedHolder.removeChild(feedHolder.lastChild);
        }

        for (var i = 0; i < feedJson.length; i++) {
            var creationTime = Date.parse(feedJson[i].creation_time);
            var deletionTime = Date.parse(feedJson[i].deletion_time);

            var now = Math.floor((new Date()).getTime());
            var opacity =  1 - (now - creationTime) / (deletionTime - creationTime);

            if (opacity > 1) {
                // should never happen assume the laws of time stay sane
                opacity = 1;
            }
            // will also probably never happen, but still don't attempt to render negative opacity
            else if (opacity > 0) {
                var feedElement = document.createElement("div");
                feedElement.className = "feed-element";

                var feedElementText = document.createElement("h4");
                feedElementText.className = "feed-element-text";
                feedElementText.innerText = feedJson[i].message;
                feedElementText.style.opacity = opacity;

                feedElement.appendChild(feedElementText);

                feedHolder.appendChild(feedElement);

            }
        }
    }

    var url = "https://whispering-pablo.herokuapp.com/api/get/feed";

    window.fetch(url, {
        method: "get"
    }).then(function (response) {
        return response.json();
    }).then(function (json) {
        renderFeed(json);
    });
}

// So that the count increments immediately upon hitting submit. Does not change anything server-side
function incrementWhisperCount() {
    var numWhispersElement = document.getElementById("num-whispers");
    var currentCount = numWhispersElement.innerText;
    numWhispersElement.innerText = parseInt(currentCount, 10) + 1;
}

function isValidSubmission(text) {
    function stringIsAllSpaces(text) {
        for (var i = 0; i < text.length; i++) {
            var c = text.charAt(i);
            if (c !== " ") {
                return false;
            }
        }

        return true;
    }

    return text.length > 0 && text.length <= 500 && !stringIsAllSpaces(text);
}

