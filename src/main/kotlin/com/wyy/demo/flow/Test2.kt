package com.wyy.demo.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking



/**
 * 流是冷的，只有流被收集的时候才运行
 */
fun simple5(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

fun main() = runBlocking {
    println("Calling simple function...")
    val flow = simple5()
    println("Calling collect...")
    flow.collect {
        println(it)
    }
    println("Calling collect again...")
    flow.collect {
        println(it)
    }
}