package com.roadmap.routes

import io.ktor.server.routing.*

fun Route.getAllConceptsRoute() {
  get("/concepts") {

  }
}

fun Route.getConceptsRoute() {
  get("/concepts/{id}") {

  }
}
