package com.wyy.demo.concurrence

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * 解决办法，互斥锁
 */

var counter5 = 0
val mutex = Mutex()
fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            mutex.withLock {
                counter5++
            }
        }
    }
    println("Counter= $counter5")
}