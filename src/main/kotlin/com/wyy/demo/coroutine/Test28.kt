package com.wyy.demo.coroutine

import jdk.nashorn.internal.objects.Global
import kotlinx.coroutines.*


/**
 * SupervisorJob 单向取消，不会把整个协程都取消了
 *
 * supervisorScope 这个作用于也可是实现相同的功能
 */
fun main() = runBlocking<Unit>() {
//    test1()
    test2()
}

suspend fun test1() = coroutineScope {
    val supervisor = SupervisorJob()
    with(CoroutineScope(coroutineContext + supervisor)) {
        // 启动第一个子作业——这个示例将会忽略它的异常(不要在实践中这么做!)
        val firstChild = launch(CoroutineExceptionHandler { coroutineContext, throwable ->  }) {
            println("The first child is failing")
            throw AssertionError("The first child is cancelled")
        }
        // 启动第二个子作业
        val secondChild = launch {
            firstChild.join()

            // 取消了第一个子作业且没有传播给第二个子作业
            println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active")
            try {
                delay(Long.MAX_VALUE)
            } finally {
                // 但是取消了监督的传播
                println("The second child is cancelled because the supervisor was cancelled")
            }
        }
        firstChild.join()
        println("Cancelling the supervisor")
        supervisor.cancel()
        secondChild.join()
    }
}

suspend fun test2() = supervisorScope {
// 启动第一个子作业——这个示例将会忽略它的异常(不要在实践中这么做!)
    val firstChild = launch(CoroutineExceptionHandler { coroutineContext, throwable ->  }) {
        println("The first child is failing")
        throw AssertionError("The first child is cancelled")
    }
    // 启动第二个子作业
    val secondChild = launch {
        firstChild.join()

        // 取消了第一个子作业且没有传播给第二个子作业
        println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active")
        try {
            delay(Long.MAX_VALUE)
        } finally {
            // 但是取消了监督的传播
            println("The second child is cancelled because the supervisor was cancelled")
        }
    }
    firstChild.join()
    println("Cancelling the supervisor")
    cancel()
    secondChild.join()
}


