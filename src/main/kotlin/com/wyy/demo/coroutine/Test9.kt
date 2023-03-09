package com.wyy.demo.coroutine

import kotlinx.coroutines.*

/**
 * 取消是协作的,正在执行的计算任务，没有检查取消的话，它是不能被取消的
 */
fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(5) {
            try {
                println("job: I'm sleeping $it ...")
                delay(500)
            } catch (e: Exception) { // 这里因为捕获了异常导致调用cancelAndJoin 不会结束，如果不捕获协程处理取消了
                println(e)
            }
        }
    }
    delay(1300L) // 等待一段时间
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // 取消一个作业并且等待它结束
    println("main: Now I can quit.")
}
