package com.wyy.demo.coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    doWorld3()
    println("Done")
}

suspend fun doWorld3() = coroutineScope {
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}