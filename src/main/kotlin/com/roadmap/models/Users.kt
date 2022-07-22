package com.roadmap.models

import org.jetbrains.exposed.sql.Table

object Users : Table() {
  val id = varchar("id", length = 255)
  val name = varchar("name", length = 50) // Column<String>
}


