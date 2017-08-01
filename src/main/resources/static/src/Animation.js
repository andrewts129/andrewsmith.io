import React from "react"

export class Animation extends React.Component {
    render() {
        return (
            <canvas id="main-canvas"></canvas>
        );
    }

    componentDidMount() {
        bgAnimation();
    }
}

function bgAnimation() {
    var canvas = document.getElementById("main-canvas");
    var context = canvas.getContext("2d");

    var circles = [];

    var dt = 10;

    function resizeCanvas() {
        context.canvas.width = window.innerWidth;
        context.canvas.height = window.innerHeight;
    }

    function Circle(x, y, radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;

        this.color = "#327ffc";

        this.update = function () {
            this.radius = this.radius * 0.97;
            this.draw()
        };

        this.draw = function () {
            context.beginPath();
            context.arc(this.x, this.y, this.radius, 0, 2 * Math.PI);
            context.fillStyle = this.color;
            context.stroke();
        };
    }

    function updateAll() {
        if (Math.random() > 0.90) {
            var newCircle = new Circle(canvas.width * Math.random(), canvas.height * Math.random(), 30 * Math.random());
            circles.push(newCircle);
        }

        context.clearRect(0, 0, canvas.width, canvas.height);

        for (var i = 0; i < circles.length; i++) {
            circles[i].update();

            if (circles[i].radius < 0.001) {
                circles.splice(i, 1);
            }
        }

        setTimeout(updateAll, dt);
    }
    resizeCanvas();
    updateAll();
}