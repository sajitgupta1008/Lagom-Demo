package com.example.hello.impl

import java.util.concurrent.TimeUnit
import Services.KafkaScalaProducer
import akka.NotUsed
import akka.actor.{ActorSystem, Cancellable}
import com.example.hello.api.{ExternalService, HellolagomService, UserData}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import org.apache.kafka.clients.producer.ProducerRecord
import play.api.Logger
import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class HellolagomServiceImpl(externalService: ExternalService)(implicit ec: ExecutionContext) extends HellolagomService {

  private val kafkaScalaProducer = new KafkaScalaProducer

  scheduleExternalService()

  def scheduleExternalService(): Cancellable = {
    val system = ActorSystem("mySystem")

    system.scheduler.schedule(Duration(0, TimeUnit.SECONDS), Duration(10, TimeUnit.SECONDS)) {
         externalService.getUser.invoke().onComplete {
        case Success(userData) =>
          Logger.info("\n---------------------------------------SCHEDULER DATA-----------------------------------\n"
            + userData.toString + "\n")

        case Failure(exception) => Logger.info("\n------------EXCEPTION\n-------------------" +
          s"$exception-----------------")
      }
    }
  }

  override def hello(name: String): ServiceCall[NotUsed, String] = ServiceCall { _ =>
    Future.successful(s"Hello, $name")
  }

  override def getUserDetails: ServiceCall[NotUsed, UserData] = ServiceCall { _ =>

    val userList: Future[UserData] = externalService.getUser.invoke()
    userList.map { userData =>
      val record: ProducerRecord[Nothing, String] = new ProducerRecord(kafkaScalaProducer.topic, userData.toString)
      kafkaScalaProducer.producer.send(record)
      userData
    }
  }
}
