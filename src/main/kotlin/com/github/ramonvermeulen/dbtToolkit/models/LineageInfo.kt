package com.github.ramonvermeulen.dbtToolkit.models

import com.github.ramonvermeulen.dbtToolkit.services.Edge
import com.github.ramonvermeulen.dbtToolkit.services.Node
import com.github.ramonvermeulen.dbtToolkit.services.toJson
import com.google.gson.JsonArray
import com.google.gson.JsonObject

data class LineageInfo(
    val nodes: MutableSet<Node>,
    val edges: MutableSet<Edge>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LineageInfo

        if (nodes != other.nodes) return false
        if (edges != other.edges) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nodes.hashCode()
        result = 31 * result + edges.hashCode()
        return result
    }
}

fun LineageInfo.toJson(): JsonObject {
    val json = JsonObject() // t.b.d.
    json.add("nodes", JsonArray().apply { nodes.forEach { add(it.toJson()) } })
    json.add("edges", JsonArray().apply { edges.forEach { add(it.toJson()) } })
    return json
}

