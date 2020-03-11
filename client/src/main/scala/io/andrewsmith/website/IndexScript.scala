package io.andrewsmith.website

import cats.effect.{IO, Timer}
import cats.implicits._
import fs2.Stream
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.{CanvasRenderingContext2D, document, window}

import scala.concurrent.ExecutionContext
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.js.timers.setTimeout
import scala.util.Random
import scala.concurrent.duration._


object IndexScript {
  private val WIDTH = window.innerWidth
  private val HEIGHT = window.innerHeight

  private val dt = 1000
  private val G = 0.0001
  private val maxSpeed = 10
  private val tooFastDampening = 0.2
  private val collisionPadding = 300
  private val bounceDampening = -0.6

  @JSExportTopLevel("indexMain")
  def main(): Unit = {
    implicit val timer: Timer[IO] = IO.timer(ExecutionContext.global)

    val context = getCanvasContext
    val stateStream: Stream[IO, List[BallState]] = Stream.unfoldLoop(buildInitialBalls())(
      ballStates => (ballStates, Some(ballStates.map(_.nextState())))
    )

    val drawingStream: Stream[IO, IO[List[Unit]]] = stateStream.map(
      ballStates => ballStates.traverse(_.draw(context))
    )

    drawingStream.metered(1.second)
      .compile
      .drain
      .unsafeRunSync()
  }

  private def getCanvasContext: CanvasRenderingContext2D = {
    val context = document
      .getElementById("animationCanvas").asInstanceOf[Canvas]
      .getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    context.canvas.width = WIDTH.toInt
    context.canvas.height = HEIGHT.toInt

    context
  }

  private def buildInitialBalls(): List[BallState] = {
    List.tabulate(15) {
      _ =>
        new BallState(
          color = {
            val red = Random.between(140, 220)
            val opacity = Random.between(0.1, 0.5)
            s"rgba($red,0,0,$opacity)"
          },
          radius = Random.between(5.0, 10.0),
          x = Random.between(0.0, WIDTH),
          y = Random.between(0.0, HEIGHT),
          vx = Random.between(-3.0, 3.0),
          vy = Random.between(-3.0, 3.0),
        )
    }
  }

  private class BallState(val radius: Double,
                          val color: String,
                          val x: Double = 0,
                          val y: Double = 0,
                          val vx: Double = 0,
                          val vy: Double = 0,
                          val ax: Double = 0,
                          val ay: Double = 0,
                          val fx: Double = 0,
                          val fy: Double = 0,
                         ) {
    private val mass: Double = (4.0 / 3) * math.Pi * math.pow(radius, 3) / 1000

    def nextState(): BallState = new BallState(
      this.radius,
      this.color,
      nextX(),
      nextY(),
      nextVx(),
      nextVy(),
      nextAx(),
      nextAy(),
      nextFx(),
      nextFy()
    )

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

    def draw(context: CanvasRenderingContext2D): IO[Unit] = IO {
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
