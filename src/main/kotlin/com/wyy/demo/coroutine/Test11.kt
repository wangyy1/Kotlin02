package com.wyy.demo.coroutine

import kotlinx.coroutines.*

/**
 * 在 finally 中释放资源
 */
fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        try {
            repeat(1000) {
                println("job: I'm sleeping $it ...")
                delay(500)
            }
        } finally {
            println("job: I'm running finally")
        }
    }
    delay(1300L) // 等待一段时间
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // 取消一个作业并且等待它结束
    println("main: Now I can quit.")
}

