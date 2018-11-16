package remote 

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import akka.actor.{ActorSystem, Props}

object Main extends App {

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
      |      port = 2552
      |    }
      |  }
      |}
    """.stripMargin)


  val system = ActorSystem("AkkaIMDB", ConfigFactory.load(config))
  val database = system.actorOf(Props(new ActorDB),"ImdbActor")
}