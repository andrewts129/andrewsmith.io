namespace Index {
    // How far off the screen the balls need to go before bouncing off the edges
    // If 0, they bounce off the edges of the screen
    const collisionPadding = 300;

    const width = window.innerWidth;
    const height = window.innerHeight;

    const dt = 1;   // Number of milliseconds between each drawing/physics calculations
    const G = 0.0001;   // Big G, gravitational constant

    // This is the object that the balls gravitate around
    const blackHole = {
        x: width / 2,
        y: height / 2,
        mass: 40000000
    };

    // If the balls go faster than the max speed, their velocity gets multiplied by the below number to slow them down
    const maxSpeed = 10;
    const tooFastDampening = 0.2;

    // Balls' velocity gets multiplied by this when bouncing off something to prevent speeds from getting too crazy
    // It looks good too
    const bounceDampening = 0.6;

    // Generates a random decimal number between a and b.
    const randNumBetween = (a: number, b: number): number => {
        return Math.random() * (b - a) + a
    };

    interface TwoDimensionalVector {
        x: number
        y: number
    }

    class Ball {
        radius: number;
        mass: number;
        color: string;
        p: TwoDimensionalVector;
        v: TwoDimensionalVector;
        a: TwoDimensionalVector;
        f: TwoDimensionalVector;

        constructor(x: number, y: number, radius: number, color: string) {
            this.radius = radius;
            this.mass = (4 / 3) * Math.PI * Math.pow(this.radius, 3) / 1000;  // Divide by 1000 bc the balls were way too heavy
            this.color = color;

            this.p = {
                x: x,
                y: y
            };

            this.v = {
                x: randNumBetween(-3, 3),
                y: randNumBetween(-3, 3)
            };

            this.a = {
                x: 0,
                y: 0
            };

            this.f = {
                x: 0,
                y: 0
            };
        }


        updatePosition = (): void => {
            this.p.x = this.p.x + (this.v.x * dt) + (0.5 * this.a.x * Math.pow(dt, 2));
            this.p.y = this.p.y + (this.v.y * dt) + (0.5 * this.a.y * Math.pow(dt, 2));
        };

        updateVelocity = (): void => {
            this.v.x = this.v.x + (this.a.x * dt);
            this.v.y = this.v.y + (this.a.y * dt);

            // Slow the ball down if it's going too fast
            if (this.v.x > maxSpeed || this.v.x < -maxSpeed) {
                this.v.x = this.v.x * tooFastDampening;
            }
            if (this.v.y > maxSpeed || this.v.y < -maxSpeed) {
                this.v.y = this.v.y * tooFastDampening;
            }
        };

        updateAcceleration = (): void => {
            this.a.x = this.f.x / this.mass;
            this.a.y = this.f.y / this.mass;
        };

        updateForce = (): void => {
            const distBetweenSq = Math.pow(blackHole.x - this.p.x, 2) + Math.pow(blackHole.y - this.p.y, 2);

            // To prevent weird behavior when the ball gets really close to the center
            if (distBetweenSq > 5000) {

                // Magnitude of the gravitational force vector
                const fMagnitude = G * this.mass * blackHole.mass / distBetweenSq;

                // Take that vector and point it towards the black hole
                const unitVectorTowardsHole = {
                    x: (blackHole.x - this.p.x) / Math.sqrt(distBetweenSq),
                    y: (blackHole.y - this.p.y) / Math.sqrt(distBetweenSq)
                };

                this.f.x = fMagnitude * unitVectorTowardsHole.x;
                this.f.y = fMagnitude * unitVectorTowardsHole.y;
            }
        };

        checkCollisionBorders = (): void => {
            // Reverses the ball's direction if it hits a boundary (usually the boundaries are pretty far outside of the
            // screen
            if ((this.p.x - this.radius + collisionPadding < 0) || (this.p.x + this.radius - collisionPadding > width)) {
                this.v.x = -this.v.x * bounceDampening;
            }

            if ((this.p.y - this.radius + collisionPadding < 0) || (this.p.y + this.radius - collisionPadding > height)) {
                this.v.y = -this.v.y * bounceDampening;
            }
        };

        draw = (context: CanvasRenderingContext2D): void => {
            // Returns true if the ball is currently on the screen
            const isVisible = (): boolean => !(this.p.x + this.radius < 0 || this.p.x - this.radius > width || this.p.y + this.radius < 0 || this.p.y - this.radius > height);

            // Only draw the ball if it's actually visible
            if (isVisible()) {
                context.beginPath();
                context.arc(this.p.x, this.p.y, this.radius, 0, 2 * Math.PI);
                context.fillStyle = this.color;
                context.fill();
            }
        };

        nextState = (): void => {
            this.updateForce();
            this.updateAcceleration();
            this.updateVelocity();
            this.updatePosition();
            this.checkCollisionBorders();
        };
    }

    // Generates a random rgba color that is mostly red and pretty transparent
    const generateColor = (): string => {
        const redValue = Math.floor(randNumBetween(140, 220));
        const opacity = randNumBetween(0.1, 0.5);

        return `rgba(${redValue},0,0,${opacity})`;
    };

    const buildBalls = (): Ball[] => Array.from({length: 15}, () => {
        const newBallX = randNumBetween(0, width);
        const newBallY = randNumBetween(0, height);

        const newBallRadius = randNumBetween(5, 10);
        return new Ball(newBallX, newBallY, newBallRadius, generateColor());
    });

    const updateAll = (context: CanvasRenderingContext2D, balls: Ball[]): void => {
        context.clearRect(0, 0, width, height);

        balls.forEach((ball) => {
            ball.nextState();
            ball.draw(context);
        });

        setTimeout(updateAll, dt, context, balls);
    };

    const main = (): void => {
        const canvas = document.getElementById("animationCanvas") as HTMLCanvasElement;
        const context = canvas.getContext("2d");

        context.canvas.width = width;
        context.canvas.height = height;

        const allBalls = buildBalls();
        updateAll(context, allBalls);  // Infinitely recursive
    };

    window.onload = main;
}
