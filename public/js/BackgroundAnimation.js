var canvas = document.getElementById("full-page-canvas");
var context = canvas.getContext("2d");

context.canvas.width = window.innerWidth;
context.canvas.height = window.innerHeight;

// How far off the screen the balls need to go before bouncing off the edges
// If 0, they bounce off the edges of the screen
var collisionPadding = 300;

var width = window.innerWidth;
var height = window.innerHeight;

var dt = 1;   // Number of milliseconds between each drawing/physics calculations
var G = 0.0001;   // Big G, gravitational constant

// This is the object that the balls gravitate around
var blackHole = {
    x: width / 2,
    y: height / 2,
    mass: 40000000
};

// If the balls go faster than the max speed, their velocity gets multiplied by the below number to slow them down
var maxSpeed = 10;
var tooFastDampening = 0.2;

// Balls' velocity gets multiplied by this when bouncing off something to prevent speeds from getting too crazy
// It looks good too
var bounceDampening = 0.6;

// Creating the balls
var allBalls = [];
for (var i = 0; i < 15; i++) {
    var newBallX = randNumBetween(0, width);
    var newBallY = randNumBetween(0, height);

    var newBallRadius = randNumBetween(5, 10);
    var newBall = new Ball(newBallX, newBallY, newBallRadius, generateColor());
    newBall.v_x = randNumBetween(-3, 3);
    newBall.v_y = randNumBetween(-3, 3);

    allBalls.push(newBall);
}

// This method calls itself again when it's done, so it runs forever basically
updateAll();

// Generates a random decimal number between a and b.
function randNumBetween(a, b) {
    return Math.random() * (b - a) + a
}

// Generates a random rgba color that is mostly red and pretty transparent
function generateColor() {
    var redValue = Math.floor(randNumBetween(140, 220));
    var opacity = randNumBetween(0.1, 0.5);

    return "rgba(" + redValue + ",0,0," + opacity + ")";
}

function updateAll() {
    context.clearRect(0, 0, width, height);

    // Do the physics calculations for each ball and draw them
    for (var i = 0; i < allBalls.length; i++) {
        var ball = allBalls[i];
        ball.update();
    }

    setTimeout(updateAll, dt);
}

function Ball(x, y, radius, color) {
    var self = this;

    this.radius = radius;
    this.mass = (4/3) * Math.PI * Math.pow(self.radius, 3) / 1000;  // Divide by 1000 bc the balls were way too heavy
    this.color = color;

    this.x = x;
    this.y = y;

    this.v_x = 2;
    this.v_y = -3;

    this.a_x = 0;
    this.a_y = 0;

    this.f_x = 0;
    this.f_y = 0;

    function updatePosition() {
        self.x = self.x + (self.v_x * dt) + (0.5 * self.a_x * Math.pow(dt, 2));
        self.y = self.y + (self.v_y * dt) + (0.5 * self.a_y * Math.pow(dt, 2));
    }

    function updateVelocity() {
        self.v_x = self.v_x + (self.a_x * dt);
        self.v_y = self.v_y + (self.a_y * dt);

        // Slow the ball down if it's going too fast
        if (self.v_x > maxSpeed || self.v_x < -maxSpeed) {
            self.v_x = self.v_x * tooFastDampening;
        }
        if (self.v_y > maxSpeed || self.v_y < -maxSpeed) {
            self.v_y = self.v_y * tooFastDampening;
        }
    }

    function updateAcceleration() {
        self.a_x = self.f_x / self.mass;
        self.a_y = self.f_y / self.mass;
    }

    function updateForce() {
        var distBetweenSq = Math.pow(blackHole.x - self.x, 2) + Math.pow(blackHole.y - self.y, 2);

        // To prevent weird behavior when the ball gets really close to the center
        if (distBetweenSq > 5000) {

            // Magnitude of the gravitational force vector
            var fMagnitude = G * self.mass * blackHole.mass / distBetweenSq;

            // Take that vector and point it towards the black hole
            var unitVectorTowardsHole = {
                x: (blackHole.x - self.x) / Math.sqrt(distBetweenSq),
                y: (blackHole.y - self.y) / Math.sqrt(distBetweenSq)
            };
            self.f_x = fMagnitude * unitVectorTowardsHole.x;
            self.f_y = fMagnitude * unitVectorTowardsHole.y;
        }
    }

    function checkCollisionBorders() {
        // Reverses the ball's direction if it hits a boundary (usually the boundaries are pretty far outside of the
        // screen
        if ((self.x - self.radius + collisionPadding < 0) || (self.x + self.radius - collisionPadding > width)) {
            self.v_x = -self.v_x * bounceDampening;
        }

        if ((self.y - self.radius + collisionPadding < 0) || (self.y + self.radius - collisionPadding > height)) {
            self.v_y = -self.v_y * bounceDampening;
        }
    }

    function draw() {
        // Returns true if the ball is currently on the screen
        function isVisible() {
            return !(self.x + self.radius < 0 || self.x - self.radius > width || self.y + self.radius < 0 || self.y - self.radius > height);
        }

        // Only draw the ball if it's actually visible
        if (isVisible()) {
            context.beginPath();
            context.arc(self.x, self.y, self.radius, 0, 2 * Math.PI);
            context.fillStyle = self.color;
            context.fill();
        }
    }

    this.update = function() {
        updateForce();
        updateAcceleration();
        updateVelocity();
        updatePosition();
        checkCollisionBorders();
        draw();
    };
}