package tasks

import akka.actor.ActorSystem
import javax.inject.Inject
import utils.BogoSorter

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class BogoStepTask @Inject()(actorSystem: ActorSystem)(implicit executionContext: ExecutionContext) {
  actorSystem.scheduler.schedule(0.seconds, 1.second) {
    BogoSorter.bogoStep()
  }
}
