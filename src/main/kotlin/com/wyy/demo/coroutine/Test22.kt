package com.wyy.demo.coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * 调度器与线程
 */



fun main() = runBlocking<Unit> {
    launch {
        println("1" + Thread.currentThread().name)
    }
    launch(Dispatchers.Unconfined) {
        println("2" + Thread.currentThread().name)
    }
    launch(Dispatchers.Default) {
        println("3" + Thread.currentThread().name)
    }
    launch(Dispatchers.IO) {
        println("4" + Thread.currentThread().name)
    }
    launch(newSingleThreadContext("自定义线程")) {
        println("5" + Thread.currentThread().name)
    }
}
