package com.github.ramonvermeulen.dbtToolkit.models

import com.google.gson.JsonObject

data class Edge(
    val parent: String,
    val child: String,
)

fun Edge.toJson(): JsonObject {
    val json = JsonObject()
    json.addProperty("parent", parent)
    json.addProperty("child", child)
    return json
}