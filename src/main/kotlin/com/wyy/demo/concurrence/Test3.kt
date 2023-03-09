package com.wyy.demo.concurrence

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * 解决办法，使用单个线程处理
 */

var counter3 = 0
val counterContext = newSingleThreadContext("CounterContext")

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            withContext(counterContext) {
                counter3++
            }
        }
    }
    println("Counter= $counter3")
}