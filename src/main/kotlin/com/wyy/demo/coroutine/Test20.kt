package com.wyy.demo.coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * 使用 async 的结构化并发
 */


suspend fun concurrentSum(): Int = coroutineScope {
    val  one = async {
        doSomethingUsefulOne2()
    }
    val two = async {
        doSomethingUsefulTwo2()
    }
    one.await() + two.await()
}

fun main() = runBlocking {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}

