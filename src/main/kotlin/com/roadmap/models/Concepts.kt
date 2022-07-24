package com.roadmap.models

import org.jetbrains.exposed.sql.Table

object Concepts : Table() {
  val id = integer("id")
  var name = varchar("name", 255)
  var link = varchar("link", 255).nullable()

  override val primaryKey = PrimaryKey(id) // name is optional here
}


data class ConceptsCsv(
  val id: Int,
  val name: String,
  val link: String?,
)

