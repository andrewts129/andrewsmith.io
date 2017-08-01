var app = angular.module("myApp", []);
app.controller("myCtrl", function ($scope, $window) {
    var canvas = document.getElementById("bg-canvas");
    var context = canvas.getContext("2d");

    var t = 0;
    var dt = 1;

    var windSpeed = 20;
    var hillHeight = 0.4;
    var allTrees = [];

    var treeColors = ["#1e7d1e", "#1b6f1b", "#176117", "#145314", "#114511", "#0d370d"];

    angular.element($window).on("resize", resizeCanvas);

    function resizeCanvas() {
        context.canvas.width = $window.innerWidth;
        context.canvas.height = $window.innerHeight;
    }

    function redraw() {

        function drawSky() {
            context.beginPath();
            context.rect(0, 0, canvas.width, canvas.height * (1 - hillHeight));
            context.fillStyle = "#7ec0ee";
            context.fill();
        }

        function drawHill() {
            context.beginPath();
            context.rect(0, canvas.height * (1 - hillHeight), canvas.width, canvas.height * hillHeight);
            context.fillStyle = "#228b22";
            context.fill();
        }

        context.clearRect(0, 0, canvas.width, canvas.height);
        drawSky();
        drawHill();
        for (var i = 0; i < allTrees.length; i++) {
            allTrees[i].draw(t);
        }

        t = t + dt;
        setTimeout(redraw, dt);
    }

    function Tree(x, y, width, height, color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.phaseShift = Math.random() * 3;

        this.draw = function (t) {
            context.beginPath();

            context.moveTo(this.x, this.y);
            context.lineTo(this.x + this.width/2 + windSpeed*windSpeed / (this.width/4) * (Math.sin(t / windSpeed / 30 + this.phaseShift) + 0.7), this.y - this.height);
            context.lineTo(this.x + this.width, this.y);

            context.fillStyle = this.color;
            context.fill();
        }
    }

    resizeCanvas();

    for (var i = 0; i < 25; i++) {
        var newTreeColor = treeColors[Math.floor(Math.random()*treeColors.length)];
        var newTree = new Tree(canvas.width * Math.random(), canvas.height * (1-hillHeight) + (Math.random() * 9), 110, 180, newTreeColor);
        allTrees.push(newTree);
    }

    redraw();
});