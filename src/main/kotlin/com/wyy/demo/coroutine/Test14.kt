package com.wyy.demo.coroutine

import kotlinx.coroutines.*

/**
 * 超时
 * withTimeoutOrNull 超时不抛出异常，返回null
 */
fun main() = runBlocking {
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) {
            println("I'm sleeping $it ...")
            delay(500L)
        }
        "Done" // 在他运行得到结果之前取消它
    }

    println("Result is $result")
}

