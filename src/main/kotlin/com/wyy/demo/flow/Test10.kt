package com.wyy.demo.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import kotlin.system.measureTimeMillis


/**
 * 展平流
 */

fun main() = runBlocking<Unit> {
//    testFlatMapConcat()
//    testFlattenConcat()
//    testFlatMapMerge()
//    testFlattenMerge()
    testFlatMapLatest()
}

/**
 * 默认情况下面代码会返回一个包含流的流
 */
suspend fun test101() {
    fun requestFlow(i: Int) = flow {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }

    (1..3).asFlow().map {
        requestFlow(it)
    }.collect {

    }
}


/**
 * flatMapConcat
 * 将流的流串联
 */
suspend fun testFlatMapConcat() {
    fun requestFlow(i: Int) = flow {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }

    val startTime = System.currentTimeMillis()
    (1..3).asFlow()
        .onEach {
            delay(100)
        }.flatMapConcat {
        requestFlow(it)
    }.collect {
            println("$it at ${System.currentTimeMillis() - startTime} ms from start")
    }
}

/**
 * flattenConcat
 * flattenConcat 展平多个流，以顺序方式将给定的流展开为单个流
 */
suspend fun testFlattenConcat() {
    flow<Flow<Int>> {
        emit(flowOf(1,2,3,4,5))
        emit(flowOf(6,7,8,9,10))
    }.flattenConcat().collect {
        println(it)
    }
}

/**
 * flatMapMerge 另一种展平操作是并发收集所有传入的流
 */
suspend fun testFlatMapMerge() {
    fun requestFlow(i: Int) = flow {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }

    val startTime = System.currentTimeMillis()
    (1..3).asFlow()
        .onEach {
            delay(100)
        }.flatMapMerge {
            requestFlow(it)
        }.collect {
            println("$it at ${System.currentTimeMillis() - startTime} ms from start")
        }
}

/***
 * flattenMerge
 * 和 flattenConcat 大抵一样，区别是可以设置并发数
 */
suspend fun testFlattenMerge() {
    flow<Flow<Int>> {
        emit(flowOf(1,2,3,4,5))
        emit(flowOf(6,7,8,9,10))
    }.flattenMerge(5).collect {
        println(it)
    }
}

/**
 * flatMapLatest
 * 与 collectLatest 操作符类似(在"处理最新值" 小节中已经描述过)，也有相对应的“最新”展平 模式，在发出新流后立即取消先前流的收集
 */
suspend fun testFlatMapLatest() {
    fun requestFlow(i: Int): Flow<String> = flow {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }
    val startTime = System.currentTimeMillis()
    (1..3).asFlow().onEach {
        delay(100)
    }.flatMapLatest {
        requestFlow(it)
    }.collect {
        println("$it at ${System.currentTimeMillis() - startTime} ms from start")
    }


}

