package com.wyy.demo.coroutine

import kotlinx.coroutines.*

/**
 * 超时
 * withTimeout 超时抛出异常
 */
fun main() = runBlocking {
    withTimeout(1300L) {
        repeat(1000) {
            println("I'm sleeping $it ...")
            delay(500L)
        }
    }
}

