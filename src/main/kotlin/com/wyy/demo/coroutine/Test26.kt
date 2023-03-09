package com.wyy.demo.coroutine

import kotlinx.coroutines.*


/**
 * 异常
 */
fun main() = runBlocking<Unit> {
    val job = GlobalScope.launch { // launch 跟协程
        println("Throwing exception from launch")
        throw IndexOutOfBoundsException()
    }
    job.join()

    println("Joined failed job")
    val deferred = GlobalScope.async { // async 跟协程
        println("Throwing exception from async")
        throw ArithmeticException()
    }
    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }
}


