package db

import swaydb._
import swaydb.serializers.Default._

object MessageQueue {
  private val messages = swaydb.persistent.Map[Int, String, Nothing, IO.ApiIO](dir = s"$swayDbFolder/messages").get

  def add(text: String): Unit = {
    val id = getLastId match {
      case Some(value) => value + 1
      case None => 0
    }

    messages.put(id, text)
  }

  def pop(): Option[String] = {
    getHead match {
      case Some(pair) =>
        deleteHead()
        Some(pair._2)
      case None => None
    }
  }

  private def getHead: Option[(Int, String)] = {
    messages.headOption.toOption match {
      case Some(pair) => pair
      case None => None
    }
  }

  private def getLastId: Option[Int] = {
    messages.lastOption.toOption match {
      case Some(Some(pair)) => Some(pair._1)
      case _ => None
    }
  }

  private def deleteHead(): Unit = {
    getHead match {
      case Some(pair) => messages.remove(pair._1)
      case None =>
    }
  }
}
