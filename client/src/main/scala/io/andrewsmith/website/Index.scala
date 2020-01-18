package io.andrewsmith.website

import org.scalajs.dom.html.Canvas
import org.scalajs.dom.{CanvasRenderingContext2D, document, window}

import scala.scalajs.js.timers.setTimeout
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.util.Random

// TODO redo this in a functional style
object Index {
  private val random = new Random()

  private val WIDTH = window.innerWidth
  private val HEIGHT = window.innerHeight

  private val dt = 1
  private val G = 0.0001
  private val maxSpeed = 10
  private val tooFastDampening = 0.2
  private val collisionPadding = 300
  private val bounceDampening = -0.6

  @JSExportTopLevel("indexMain")
  def main(): Unit = {
    val context = getCanvasContext
    val balls = buildBalls()
    redraw(context, balls)
  }

  private def redraw(context: CanvasRenderingContext2D, balls: Vector[Ball]): Unit = {
    context.clearRect(0, 0, WIDTH, HEIGHT)
    balls.foreach { ball =>
      ball.nextState()
      ball.draw(context)
    }

    setTimeout(dt) {
      redraw(context, balls)
    }
  }

  private def randomBetween(a: Double, b: Double): Double = random.nextDouble() * (b - a) + a

  private def getCanvasContext: CanvasRenderingContext2D = {
    val context = document
      .getElementById("animationCanvas").asInstanceOf[Canvas]
      .getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    context.canvas.width = WIDTH.toInt
    context.canvas.height = HEIGHT.toInt

    context
  }

  private def buildBalls(): Vector[Ball] = {
    Vector.tabulate(15) {
      _ => new Ball(
        initialX = randomBetween(0, WIDTH),
        initialY = randomBetween(0, HEIGHT),
        radius = randomBetween(5, 10),
        initialVx = randomBetween(-3, 3),
        initialVy = randomBetween(-3, 3),
        color = {
          val red = randomBetween(140, 220).toInt
          val opacity = randomBetween(0.1, 0.5)
          s"rgba($red,0,0,$opacity)"
        }
      )
    }
  }

  private class Ball(initialX: Double, initialY: Double, initialVx: Double, initialVy: Double, radius: Double, color: String) {
    private val mass: Double = (4.0 / 3) * math.Pi * math.pow(radius, 3) / 1000

    private var x: Double = initialX
    private var y: Double = initialY
    private var vx: Double = initialVx
    private var vy: Double = initialVy
    private var ax: Double = 0
    private var ay: Double = 0
    private var fx: Double = 0
    private var fy: Double = 0

    def nextState(): Unit = {
      this.fx = nextFx()
      this.fy = nextFy()
      this.ax = nextAx()
      this.ay = nextAy()
      this.vx = nextVx()
      this.vy = nextVy()
      this.x = nextX()
      this.y = nextY()
    }

    private def nextX(): Double = this.x + (this.vx * dt) + (0.5 * this.ax * math.pow(dt, 2))

    private def nextY(): Double = this.y + (this.vy * dt) + (0.5 * this.ay * math.pow(dt, 2))

    private def nextVx(): Double = {
      var result = this.vx + (this.ax * dt)

      if (math.abs(result) > maxSpeed) {
        result *= tooFastDampening
      }
      if ((this.x - this.radius + collisionPadding < 0) || (this.x + this.radius - collisionPadding > WIDTH)) {
        result *= bounceDampening
      }

      result
    }

    private def nextVy(): Double = {
      var result = this.vy + (this.ay * dt)

      if (math.abs(result) > maxSpeed) {
        result *= tooFastDampening
      }
      if ((this.y - this.radius + collisionPadding < 0) || (this.y + this.radius - collisionPadding > HEIGHT)) {
        result *= bounceDampening
      }

      result
    }

    private def nextAx(): Double = this.fx / this.mass

    private def nextAy(): Double = this.fy / this.mass

    private def nextFx(): Double = {
      val distBetweenSq = math.pow(blackHole.x - this.x, 2) + math.pow(blackHole.y - this.y, 2)

      if (distBetweenSq < 5000) {
        this.fx
      } else {
        val magnitude = G * this.mass * blackHole.mass / distBetweenSq
        val direction = (blackHole.x - this.x) / Math.sqrt(distBetweenSq)
        magnitude * direction
      }
    }

    private def nextFy(): Double = {
      val distBetweenSq = math.pow(blackHole.x - this.x, 2) + math.pow(blackHole.y - this.y, 2)

      if (distBetweenSq < 5000) {
        this.fy
      } else {
        val magnitude = G * this.mass * blackHole.mass / distBetweenSq
        val direction = (blackHole.y - this.y) / Math.sqrt(distBetweenSq)
        magnitude * direction
      }
    }

    def draw(context: CanvasRenderingContext2D): Unit = {
      context.beginPath()
      context.arc(this.x, this.y, this.radius, 0, 2 * math.Pi)
      context.fillStyle = this.color
      context.fill()
    }
  }

  private object blackHole {
    val x: Double = WIDTH / 2
    val y: Double = HEIGHT / 2
    val mass: Double = 40000000
  }
}
