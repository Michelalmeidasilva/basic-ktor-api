package com.roadmap.models

import org.jetbrains.exposed.sql.Table

object Concepts : Table() {
  val id = integer("id")
  var name = varchar("name", 255)
  var link = varchar("link", 255).nullable()

  override val primaryKey = PrimaryKey(id) // name is optional here
}

object ConceptWithRelation : Table() {
  val id = integer("id").autoIncrement()

  val parentId = (integer("parent_id") references Concepts.id) // Column<Int?>
  val childId = (integer("child_id") references Concepts.id) // Column<Int?>
  val description = varchar("description", 255).nullable()

  override val primaryKey = PrimaryKey(id) // name is optional here
}

data class ConceptWithRelationCsv (
  val id: Int?,
  val parentId: Int,
  val childId: Int,
  val description: String?
)

data class ConceptsCsv (
  val id: Int,
  val name: String,
  val link: String?,
)

