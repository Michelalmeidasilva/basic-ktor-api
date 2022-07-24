package com.roadmap

import com.roadmap.helpers.importCsv
import com.roadmap.helpers.importCsvRelations
import com.roadmap.models.ConceptWithRelation
import com.roadmap.models.Concepts
import com.roadmap.models.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseConnection {

  companion object {

    fun initDB() {
      Database.connect(
        "jdbc:mysql://localhost:3306/roadmap", driver = "com.mysql.cj.jdbc.Driver",
        user = "root", password = "roadmap"
      )

      val conceptsCsv = importCsv()
      val conceptsRelationsCsv = importCsvRelations()

      transaction {
        SchemaUtils.create(Users, Concepts, ConceptWithRelation)
      }

      transaction {
        conceptsCsv.forEach { value ->
          Concepts.insert { concept ->
            concept[name] = value.name
            concept[link] = value.link
            concept[id] = value.id
          }
        }
      }

      transaction {
        conceptsRelationsCsv.forEach { value ->
          ConceptWithRelation.insert { concept ->
            concept[description] = value.description
            concept[parentId] = value.parentId
            concept[childId] = value.childId
          }
        }
      }
    }
  }

}