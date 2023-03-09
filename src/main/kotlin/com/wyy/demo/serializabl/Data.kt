package com.wyy.demo.serializabl

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Data(val a: Int, val b: String)


fun main() {
    val json = Json.encodeToString(Data(42, "Str"))
    println(json)
    val dataList = listOf(Data(42, "Str"), Data(12, "test"))
    val jsonList = Json.encodeToString(dataList)
    println(jsonList)

    val dataList2 = Json.decodeFromString<List<Data>>("""[{"a":42,"b":"Str"},{"a":12,"b":"test"}]""");
    println(dataList2)
}