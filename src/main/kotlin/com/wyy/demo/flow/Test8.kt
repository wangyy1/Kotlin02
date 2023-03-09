package com.wyy.demo.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import kotlin.system.measureTimeMillis


/**
 * 操作符
 */

fun main() = runBlocking<Unit> {
//    testFlowOn()
//    test3()
//    testBuffer()
//    testConflate()
    testCollectLatest()
}

/**
 * flowOn 操作符，该函数用于更改流发射的上下文
 */
suspend fun testFlowOn() {
    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            Thread.sleep(100) // 假装我们以消耗CPU的方式进行计算
            log("Emitting $i")
            emit(i)
        }
    }.flowOn(Dispatchers.Default) // 在流构建器中改变消耗CPU代码上下文的正确方式

    simple().collect {
        log("Collected $it")
    }
}

/**
 * 测试 流的发射很慢，流的收集也很慢
 */
suspend fun test3() {
    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            Thread.sleep(100) // 假装我们以消耗CPU的方式进行计算
            emit(i)
        }
    }

   val time =  measureTimeMillis {
        simple().collect {
            delay(300)
            log("Collected $it")
        }
    }
    println("Collected in $time ms")

}

/**
 * 缓冲 操作符来并发运行这个 simple 流中发射元素的代码以及收集的 代码
 */
suspend fun testBuffer() {
    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100) // 假装我们以消耗CPU的方式进行计算
            emit(i)
        }
    }

    val time =  measureTimeMillis {
        simple()
            .buffer()
            .collect {
                delay(300)
                log("Collected $it")
             }
    }
    println("Collected in $time ms")
}

/**
 * 合并，当收集齐处理他们太慢的时候 conflate操作符可以跳过中间值
 */
suspend fun testConflate() {
    fun simple(): Flow<Int> = flow {
        for (i in 1..5) {
            delay(100) // 假装我们以消耗CPU的方式进行计算
            emit(i)
        }
    }

    val time =  measureTimeMillis {
        simple()
            .conflate()
            .collect {
                delay(500)
                log("Collected $it")
            }
    }
    println("Collected in $time ms")
}

/**
 * 处理最新值
 * 当发射器和收集器都很慢的时候，合并是加快处理速度的一种方式。它通过删除发射值来实 现。另一种方式是取消缓慢的收集器，并在每次发射新值的时候重新启动它
 */
suspend fun testCollectLatest() {
    fun simple(): Flow<Int> = flow {
        for (i in 1..5) {
            delay(100) // 假装我们以消耗CPU的方式进行计算
            emit(i)
        }
    }

    val time =  measureTimeMillis {
        simple()
            .conflate()
            .collectLatest {
                println("Collecting $it")
                delay(300)
                log("Done $it")
            }
    }
    println("Collected in $time ms")
}