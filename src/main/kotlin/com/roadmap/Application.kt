package com.roadmap

import com.roadmap.models.Concepts
import com.roadmap.models.Users
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.roadmap.plugins.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*


fun main(args: Array<String>) {
  println("Init server")

  embeddedServer(Netty, port = 8080, host = "0.0.0.0", watchPaths = listOf("classes")) {
    initDB()
    configureRouting()
    configureSerialization()
  }.start(wait = true)

}

fun getRandomString(length: Int) : String {
  val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

  return List(length) { charset.random() }
    .joinToString("")
}

fun initDB(){
  Database.connect("jdbc:mysql://localhost:3306/roadmap", driver = "com.mysql.cj.jdbc.Driver",
    user = "root", password = "roadmap");

  transaction {
    SchemaUtils.create(Users, Concepts)
  }

  transaction {

    val theFundamentals = Concepts.insert {
      it[name] = "The fundamentals"
      it[conceptId] = null
    } get Concepts.id

    val pickALanguage = Concepts.insert {
      it[name] = "Pick a Language"
      it[conceptId] = null
    } get Concepts.id

    Concepts.insert {
      it[name] = "Kotlin"
      it[conceptId] = pickALanguage
    }

    Concepts.insert {
      it[name] = "Java"
      it[conceptId] = pickALanguage
    }

    Concepts.insert {
      it[name] = "Install Android Studio"
      it[conceptId] = theFundamentals
    }




  }
}


