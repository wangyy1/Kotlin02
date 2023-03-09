package com.wyy.demo.coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * 使用async并发
 */

suspend fun doSomethingUsefulOne2(): Int {
    delay(1000L)
    return 13
}

suspend fun doSomethingUsefulTwo2(): Int {
    delay(1000L)
    return 29
}

fun main() = runBlocking {
    val time = measureTimeMillis {
        val one = async {
            doSomethingUsefulOne2()
        }
        val two = async {
            doSomethingUsefulTwo2()
        }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

