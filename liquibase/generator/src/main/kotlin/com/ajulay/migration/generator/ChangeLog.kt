package com.ajulay.migration.generator

data class ChangeLog(
    val databaseChangeLog: List<ChangeSet>,
)

data class ChangeSet(
    val changeSet: ChangeSetData,
)

data class ChangeSetData(
    val id: String,
    val changes: List<SqlFile>,
    val author: String = "generated",
)

data class SqlFile(
    val sqlFile: SqlFileData,
)

data class SqlFileData(
    val path: String,
    val relativeToChangelogFile: Boolean = true,
    val endDelimiter: String? = null,
)
