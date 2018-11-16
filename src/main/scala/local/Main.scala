package local

import akka.util.Timeout
import scala.concurrent.duration._
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.pattern.{ask, pipe}
import scala.util._

object Main extends App {

  implicit val timeout = Timeout(1 seconds)

  val config =  ConfigFactory.parseString(
    """
      |akka {
      |  actor {
      |    provider = "akka.remote.RemoteActorRefProvider"
      |  }
      |  remote {
      |    enabled-transports = ["akka.remote.netty.tcp"]
      |    netty.tcp {
      |      hostname = "127.0.0.1"
      |      port = 2553
      |    }
      |  }
      |}
    """.stripMargin)
  val system = ActorSystem("ClientSystem", ConfigFactory.load(config))
  val DBreference = system.actorSelection(s"akka.tcp://AkkaIMDB@127.0.0.1:2552/user/ImdbActor")

  var key: String = ""
  var value: Object = None
  import scala.concurrent.ExecutionContext.Implicits.global

    println("Type S to send or R to receive a (key value) pair")
    (DBreference ? Set(key , value)).onComplete {
      case Success(x) => println("InLocal "+ x)
    }

}