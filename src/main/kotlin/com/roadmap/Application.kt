package com.roadmap

import com.roadmap.models.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.roadmap.plugins.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File


fun main() {
  println("Init server")

  embeddedServer(Netty, port = 8080, host = "0.0.0.0", watchPaths = listOf("classes")) {
    initDB()
    configureRouting()
    configureSerialization()
  }.start(wait = true)

}

fun importCsv(): MutableList<ConceptsCsv>{
  val file = File("/Users/user/michel/Personal/roadmap/src/main/resources/roadmap.csv")

  val bufferedReader =  file.bufferedReader()

  val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT
    .withFirstRecordAsHeader()
    .withIgnoreHeaderCase()
    .withTrim())

  val conceptList: MutableList<ConceptsCsv> = mutableListOf()

  for (csvRecord in csvParser) {
    val id =  Integer.parseInt(csvRecord.get("id"))
    val name = csvRecord.get("name")
    val linkCsv: String? = csvRecord.get("link")


    val linkParsed: String? = if(linkCsv != "null") linkCsv else  null

    conceptList.add(ConceptsCsv(id, name, linkParsed))
  }

  return conceptList
}

fun importCsvRelations(): MutableList<ConceptWithRelationCsv>{
  val file = File("/Users/user/michel/Personal/roadmap/src/main/resources/concept_with_relation.csv")

  val bufferedReader =  file.bufferedReader()

  val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT
    .withFirstRecordAsHeader()
    .withIgnoreHeaderCase()
    .withTrim())

  val conceptList: MutableList<ConceptWithRelationCsv> = mutableListOf()

  for (csvRecord in csvParser) {
    val description = csvRecord.get("description")
    val parentId: Int = Integer.parseInt(csvRecord.get("parent_id"))
    val childId: Int = Integer.parseInt(csvRecord.get("child_id"))

    conceptList.add(ConceptWithRelationCsv(id = null, parentId, childId, description ))
  }

  return conceptList
}
fun initDB(){
  Database.connect("jdbc:mysql://localhost:3306/roadmap", driver = "com.mysql.cj.jdbc.Driver",
    user = "root", password = "roadmap")

  val conceptsCsv = importCsv()
  val conceptsRelationsCsv = importCsvRelations()

  transaction {
    SchemaUtils.create(Users, Concepts, ConceptWithRelation)
  }

  transaction {
    conceptsCsv.forEach {  value ->
       Concepts.insert { concept ->
        concept[name] = value.name
        concept[link] = value.link
        concept[id] = value.id
      }
    }
  }

  transaction {
    conceptsRelationsCsv.forEach {  value ->
      ConceptWithRelation.insert { concept ->
        concept[description] = value.description
        concept[parentId] = value.parentId
        concept[childId] = value.childId
      }
    }
  }

}


