package com.wyy.demo.coroutine

import kotlinx.coroutines.*

/**
 * 取消协程的执行
 */
fun main() = runBlocking {
    var job = launch {
        repeat(100_000) {
            println("job: I'm sleeping $it ...")
            delay(500L)
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancel()
    job.join()
    println("main: Now I can quit.")
}
