package com.github.ramonvermeulen.dbtToolkit.models

import com.google.gson.JsonArray
import com.google.gson.JsonObject

data class Node(
    val id: String,
    val tests: List<String> = listOf(),
    val isSelected: Boolean = false,
    val relativePath: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        // isSelected is not considered in the equality check
        // to prevent unnecessary re-renders of the UI lineage graph
        if (id != other.id) return false
        if (tests != other.tests) return false
        if (relativePath != other.relativePath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + tests.hashCode()
        result = 31 * result + relativePath.hashCode()
        return result
    }
}

fun Node.toJson(): JsonObject {
    val json = JsonObject()
    json.addProperty("id", id)
    json.addProperty("isSelected", isSelected)
    json.add("tests", JsonArray().apply { tests.forEach { add(it) } })
    json.addProperty("relativePath", relativePath)
    return json
}
