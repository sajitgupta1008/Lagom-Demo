package Services

import java.util.Properties

import com.example.hello.api.UserData
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.concurrent.ExecutionContext.Implicits.global


class KafkaScalaProducer {

  val props: Properties = configureProducer()
  val topic = "kafkatopic"
  val producer: KafkaProducer[Nothing, String] = new KafkaProducer[Nothing, String](props)

  private def configureProducer(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("client.id", "ScalaProducerExample")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks", "all")
    props.put("retries", "0")
    props.put("batch.size", "16384")
    props.put("linger.ms", "1")
    props.put("buffer.memory", "33554432")
    props
  }
}


