package com.wyy.demo.concurrence

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * 解决办法，使用原子类
 */

var counter2 = AtomicInteger()

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            counter2.incrementAndGet()
        }
    }
    println("Counter= $counter2")
}