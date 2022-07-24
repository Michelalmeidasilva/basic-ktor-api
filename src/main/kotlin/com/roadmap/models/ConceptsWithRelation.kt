package com.roadmap.models

import org.jetbrains.exposed.sql.Table

object ConceptWithRelation : Table() {
  val id = integer("id").autoIncrement()

  val parentId = (integer("parent_id") references Concepts.id) // Column<Int?>
  val childId = (integer("child_id") references Concepts.id) // Column<Int?>
  val description = varchar("description", 255).nullable()

  override val primaryKey = PrimaryKey(id) // name is optional here
}

data class ConceptWithRelationCsv(
  val id: Int?,
  val parentId: Int,
  val childId: Int,
  val description: String?
)
