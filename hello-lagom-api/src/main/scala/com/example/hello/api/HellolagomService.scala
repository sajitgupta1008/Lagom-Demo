package com.example.hello.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait HellolagomService extends Service {

  def hello(id: String): ServiceCall[NotUsed, String]

  def getUserDetails: ServiceCall[NotUsed, UserData]

  override final def descriptor: Descriptor = {
    import Service._
    named("hello-lagom")
      .withCalls(
        pathCall("/api/hello/:id", hello _),
        pathCall("/api/getuser", getUserDetails)
      ).withAutoAcl(true)
  }
}

