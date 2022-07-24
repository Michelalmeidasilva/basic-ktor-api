package com.roadmap.plugins

import com.roadmap.routes.*
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {

    routing {
      getAllConceptsRoute()
      getConceptsRoute()
    }
}
