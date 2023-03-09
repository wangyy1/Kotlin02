package com.wyy.demo.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun simple(): List<Int> {
    return listOf(1,2,3)
}

/**
 * 序列
 */
fun simple2(): Sequence<Int> = sequence {
    for (i in 1..3) {
        Thread.sleep(100)
        yield(i)
    }
}

/**
 * 挂起函数，不阻塞主线程
 */
suspend fun simple3(): List<Int> {
    delay(1000)
    return listOf(1,2,3)
}

/**
 * 流
 */
fun simple4(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

//fun main() {
//    simple2().forEach {
//        println(it)
//    }
//}
fun main() = runBlocking {
//    simple2().forEach {
//        println(it)
//    }

    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(1000)
        }
    }
    simple4().collect {
        println(it)
    }

    println("----")
}