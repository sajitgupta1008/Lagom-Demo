package com.example.consumer

import java.util.{Collections, Properties}
import org.apache.kafka.clients.consumer.KafkaConsumer

class KafkaScalaConsumer {

  val props: Properties = configureConsumer()
  val topic = "kafkatopic"
  val consumer: KafkaConsumer[Nothing, String] = new KafkaConsumer[Nothing, String](props)
  consumer.subscribe(Collections.singletonList(topic))

  private def configureConsumer(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "consumer-group-1")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("auto.offset.reset", "earliest")
    props.put("session.timeout.ms", "30000")
    props
  }
}
