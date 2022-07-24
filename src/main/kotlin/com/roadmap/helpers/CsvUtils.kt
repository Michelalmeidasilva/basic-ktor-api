package com.roadmap.helpers

import com.roadmap.models.ConceptWithRelationCsv
import com.roadmap.models.ConceptsCsv
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.File


fun importCsv(): MutableList<ConceptsCsv> {
  val file = File("/Users/user/michel/Personal/roadmap/src/main/resources/roadmap.csv")

  val bufferedReader = file.bufferedReader()

  val csvParser = CSVParser(
    bufferedReader, CSVFormat.DEFAULT
      .withFirstRecordAsHeader()
      .withIgnoreHeaderCase()
      .withTrim()
  )

  val conceptList: MutableList<ConceptsCsv> = mutableListOf()

  for (csvRecord in csvParser) {
    val id = Integer.parseInt(csvRecord.get("id"))
    val name = csvRecord.get("name")
    val linkCsv: String? = csvRecord.get("link")


    val linkParsed: String? = if (linkCsv != "null") linkCsv else null

    conceptList.add(ConceptsCsv(id, name, linkParsed))
  }

  return conceptList
}

fun importCsvRelations(): MutableList<ConceptWithRelationCsv> {
  val file = File("/Users/user/michel/Personal/roadmap/src/main/resources/concept_with_relation.csv")

  val bufferedReader = file.bufferedReader()

  val csvParser = CSVParser(
    bufferedReader, CSVFormat.DEFAULT
      .withFirstRecordAsHeader()
      .withIgnoreHeaderCase()
      .withTrim()
  )

  val conceptList: MutableList<ConceptWithRelationCsv> = mutableListOf()

  for (csvRecord in csvParser) {
    val description = csvRecord.get("description")
    val parentId: Int = Integer.parseInt(csvRecord.get("parent_id"))
    val childId: Int = Integer.parseInt(csvRecord.get("child_id"))

    conceptList.add(ConceptWithRelationCsv(id = null, parentId, childId, description))
  }

  return conceptList
}