package com.wyy.demo.coroutine

import kotlinx.coroutines.*

/**
 * 异步超时和资源
 */

var acquired = 0

class Resource {
    init {
        acquired++
    }
    fun close() {
        acquired--
    }
}

fun main() {
    runBlocking {
        repeat(100_000) {
            launch {
                var resource: Resource? = null
                try {
                    withTimeout(60) {
                        delay(50)
                        resource = Resource()
                    }
                } catch (e: Exception) {
                    resource?.apply {
                        println("不为空")
                    }

                } finally {
                    resource?.close()
                }
            }
        }
    }
    println(acquired)
}

