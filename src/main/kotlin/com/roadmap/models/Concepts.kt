package com.roadmap.models

import org.jetbrains.exposed.sql.Table

object Concepts : Table() {
  val id = integer("id").autoIncrement()
  var name = varchar("name", 255)
  val conceptId = (integer("concept_id") references id).nullable() // Column<Int?>

  override val primaryKey = PrimaryKey(id) // name is optional here
}


