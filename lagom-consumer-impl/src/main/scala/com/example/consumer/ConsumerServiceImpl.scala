package com.example.consumer

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import org.apache.kafka.clients.consumer.ConsumerRecords
import scala.collection.JavaConverters._
import scala.concurrent.Future

class ConsumerServiceImpl extends ConsumerService {

  private val kafkaScalaConsumer = new KafkaScalaConsumer

  while (true) {
    val records: ConsumerRecords[Nothing, String] = kafkaScalaConsumer.consumer.poll(100)
    for (record <- records.asScala) {
      println("\n--------------------------------------Consumed from KAFKA on user hit---------------------------\n"
        + record.value() + "\n")
    }
  }

  override def hello(name: String): ServiceCall[NotUsed, String] = ServiceCall { _ =>
    Future.successful(s"Hello, $name")
  }
}
