package remote

import akka.actor.Actor
import java.util.HashMap
import akka.event.Logging
import scala.util._
import common._

class ActorDB extends Actor {

  val data = new HashMap[String, Object]
  val log = Logging(context.system, this)

  override def receive = {
      case Set(key, value) =>
        log.info("received the pair:" + key +","+ value)
        data.put(key, value)
        sender() ! "Success"

      case Get(key) =>
      val value = data.get(key)
        value match {
          case Some(v) =>
            log.info("found the pair"+ key + "," + v)
            sender() ! v
          case None =>
            log.info("the key:" + key + ", has no corresponding value in the database")
        }

      case msg => {
        println("In ActorDB "+ msg)
        sender() ! "received message "+ msg
      }
  }
}
