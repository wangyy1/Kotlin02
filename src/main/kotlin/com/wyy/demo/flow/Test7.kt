package com.wyy.demo.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import kotlin.system.measureTimeMillis


/**
 * 流上下文，默认的， flow { ... } 构建器中的代码运行在相应流的收集器提供的上下文中
 */

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking<Unit> {
//    test1()
//    test2()
//    testFlowOn()
//    test3()
//    testBuffer()
//    testConflate()
    testCollectLatest()
}

suspend fun test1() {
    fun simple(): Flow<Int> = flow {
        log("Started simple flow")
        for (i in 1..3) {
            emit(i)
        }
    }

    simple().collect {
        log("Collected $it")
    }
}

/**
 * 使用withContext时有一个常见的陷阱

 */
suspend fun test2() {
    fun simple(): Flow<Int> = flow {
        // 在流构造器中更改消耗 CPU 代码的上下文的错误方式
        log("Started simple flow")
        withContext(Dispatchers.Default) {
            for (i in 1..3) {
                emit(i)
            }
        }
    }

    simple().collect {
        log("Collected $it")
    }
}