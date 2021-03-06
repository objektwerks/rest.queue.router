package rest.queue.router

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest

import com.typesafe.config.ConfigFactory

import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

import org.scalatest.BeforeAndAfterAll
import org.slf4j.LoggerFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class QueueRouterTest extends AnyWordSpec with Matchers with ScalatestRouteTest with BeforeAndAfterAll {
  val log = LoggerFactory.getLogger(this.getClass)
  val actorRefFactory = ActorSystem.create("queue-router", ConfigFactory.load("test.akka.conf"))
  val router = new QueueRouter(ConfigFactory.load("test.queue.router.conf").as[QueueConnectorConf]("queue"))
  import router._
  val server = Http()
    .newServerAt("localhost", 0)
    .bindFlow(routes)

  override protected def afterAll(): Unit = {
    server.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }

  "push" should {
    "push message to queue." in {
      Post("/push", PushToQueue(id = "test", body = "push message")) ~> routes ~> check {
        status shouldBe StatusCodes.OK
      }
    }
  }

  "pull" should {
    "pull message from queue" in {
      Post("/pull", PullFromQueue(id = "test")) ~> routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[PulledFromQueue].body.nonEmpty shouldBe true
      }
    }
  }

  "consume" should {
    "consume messages from queue." in {
      val message = "cfg message body"
      val conf = ConfigFactory.load("test.queue.router.conf").as[QueueConnectorConf]("queue")
      val queue = new QueueConnector(QueueConnectorConf.copy(id = "test", conf))
      queue.push(message)
      queue.close()
      Post("/consume", ConsumeFromQueue(id = "test")) ~> routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[ConsumedFromQueue].responses.nonEmpty shouldBe true
      }
    }
  }
}