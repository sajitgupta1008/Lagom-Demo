package com.example.consumer

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait ConsumerService extends Service {

  def hello(name:String):ServiceCall[NotUsed,String]

  override final def descriptor: Descriptor = {
    import Service._
    named("consumer")
      .withCalls(
        pathCall("/api/hello/:id", hello _)
      ).withAutoAcl(true)
  }
}
