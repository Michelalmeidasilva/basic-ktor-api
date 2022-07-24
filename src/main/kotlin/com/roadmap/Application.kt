package com.roadmap

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.roadmap.plugins.*


fun main() {
  println("Init server")

  embeddedServer(Netty, port = 8080, host = "0.0.0.0", watchPaths = listOf("classes")) {
    DatabaseConnection.initDB()
    configureRouting()
    configureSerialization()
  }.start(wait = true)

}



