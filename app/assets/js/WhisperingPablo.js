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
        document.getElementById("connecting-label").innerText = "Messages expire after an hour";
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
        })

    }

    updateWhisperCount();
    updateFeed();
}

function updateFeed() {
    function renderFeed(feedJson) {
        var feedHolder = document.getElementById("feed-holder");

        var feed = document.createElement("div");
        feed.id = "feed";

        for (var i = 0; i < feedJson.length; i++) {
            // GMT is needed to ensure Date.parse doesn't try and mess with timezones
            var creationTime = Date.parse(feedJson[i].creation_time );
            var deletionTime = Date.parse(feedJson[i].deletion_time );

            //https://stackoverflow.com/questions/32252565/javascript-parse-utc-date
            var current = new Date();
            var now = Math.floor(new Date(current.getTime() + current.getTimezoneOffset() * 60000).getTime());
            var opacity = 1 - ((now - creationTime) / (deletionTime - creationTime));

            var feedElement = document.createElement("div");
            feedElement.className = "feed-element";
            feedElement.style.opacity = opacity;

            var feedElementText = document.createElement("p");
            feedElementText.className = "feed-element-text";
            feedElementText.innerText = feedJson[i].message;

            feedElement.appendChild(feedElementText);

            // Don't add a divider to the last message
            if (i < feedJson.length - 1) {
                var feedElementDivider = document.createElement("hr");
                feedElementDivider.className = "feed-divider";
                feedElement.appendChild(feedElementDivider);
            }

            feed.appendChild(feedElement);
        }

        // Clears the feed-holder div
        if (feedHolder.childElementCount > 0) {
            feedHolder.removeChild(feedHolder.lastChild);
        }

        // Adds the new feed
        feedHolder.appendChild(feed);
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

