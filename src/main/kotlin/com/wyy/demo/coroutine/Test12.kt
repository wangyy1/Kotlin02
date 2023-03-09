package com.wyy.demo.coroutine

import kotlinx.coroutines.*

/**
 * 运行不能取消的代码块
 * 当调用cancelAndJoin后再finally中调用的挂起函数也会跟着取消，使用下面的方式不会被取消
 */
fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        try {
            repeat(1000) {
                println("job: I'm sleeping $it ...")
                delay(500)
            }
        } finally {
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }
    delay(1300L) // 等待一段时间
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // 取消一个作业并且等待它结束
    println("main: Now I can quit.")
}

