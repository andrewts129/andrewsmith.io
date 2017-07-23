$(document).ready(function () {

    var text = $("#enter");

    text.click(function() {
        text.addClass("hide-text");
        $("#sound-on").addClass("hide-text");

        setTimeout(beginFade, 5000);

        var bg = $("#night-visions");
        var dan = $("#dan");
        var shrug = $("#shrug");

        function beginFade() {
            setTimeout(function(){
                bg.addClass("full-bright")}, 11000);

            setTimeout(function () {
                document.title = "The Imagine Dragons Appreciation Society";
            }, 8000);

            var sound = new Audio("radioactive.mp3");
            sound.volume = 0.01;
            sound.play();
            setTimeout(increaseVolume, 1000);
            setTimeout(danReynolds, 28000);
            setTimeout(fadeout, 161000);

            function increaseVolume() {
                sound.volume = sound.volume + 0.025;

                if (sound.volume < 0.97) {
                    setTimeout(increaseVolume, 1000);
                }
            }

            function danReynolds() {
                dan.addClass("mid-bright");
            }

            function fadeout() {
                dan.addClass("dark");
                bg.addClass("dark");
                shrug.removeClass("dark");
                shrug.addClass("full-bright");
            }
        }
    });
});