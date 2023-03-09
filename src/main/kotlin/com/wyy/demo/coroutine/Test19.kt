package com.wyy.demo.coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * async 风格的函数，不推荐这么使用
 */


fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne2()
}

fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo2()
}

fun main() {
    val time = measureTimeMillis {
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }

    }
    println("Completed in $time ms")
}

