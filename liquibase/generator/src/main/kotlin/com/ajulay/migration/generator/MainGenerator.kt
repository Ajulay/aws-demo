package com.ajulay.migration.generator

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import liquibase.util.FilenameUtil
import java.io.File
import java.nio.file.Files


const val GO_DELIMITER = "GO"
const val RESOURCE_DIR = "../migration/src/main/resources/db/changelog"

fun main() {
    val objectMapper = ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))
        .setSerializationInclusion(Include.NON_NULL)

    generateChangelogFile(objectMapper)
}

private fun generateChangelogFile(objectMapper: ObjectMapper) {
    val root = File(RESOURCE_DIR)

    val changeSet = root.walkTopDown()
        .filter { it.isFile && it.extension == "sql" }
        .sortedBy { it.name }
        .map {
            ChangeSet(
                ChangeSetData(
                    FilenameUtil.getDirectory(it.name),
                    listOf(
                        SqlFile(
                            SqlFileData(
                                path = it.absolutePath.removePrefix("${File(RESOURCE_DIR).absolutePath}/"),
                                endDelimiter = getEndDelimiter(it),
                            ),
                        ),
                    ),
                ),
            )
        }
        .toList()

    objectMapper.writeValue(File("${root.absolutePath}/db.changelog-master.yaml"), ChangeLog(changeSet))
}

fun getEndDelimiter(file: File): String? {
    return if (Files.readAllLines(file.toPath()).last() == GO_DELIMITER) {
        GO_DELIMITER
    } else {
        null
    }
}