$(document).ready(function () {
    setTimeout(beginFade, 3000);

    var bg = $("#bg");

    function beginFade() {
        setTimeout(function(){
            bg.removeClass("dark")}, 11000);

        setTimeout(function () {
            document.title = "The Imagine Dragons Appreciation Society";
        }, 8000);

        var sound = new Audio("radioactive.mp3");
        sound.volume = 0.01;
        sound.play();
        setTimeout(increaseVolume, 1000);
        setTimeout(danReynolds, 28000);

        function increaseVolume() {
            sound.volume = sound.volume + 0.025;

            if (sound.volume < 0.97) {
                setTimeout(increaseVolume, 1000);
            }
        }

        function danReynolds() {
            var dan = $("#dan");
            dan.addClass("notdark");
            dan.removeClass("dark");
        }

        setTimeout(function(){bg.addClass("dark")}, 160000);
    }
});