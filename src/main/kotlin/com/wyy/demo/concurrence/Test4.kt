package com.wyy.demo.concurrence

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * 解决办法，使用单个线程处理
 */

var counter4 = 0
val counterContext2 = newSingleThreadContext("CounterContext")

fun main() = runBlocking {
    withContext(counterContext2) {
        massiveRun {
            counter4++
        }
    }
    println("Counter= $counter4")
}