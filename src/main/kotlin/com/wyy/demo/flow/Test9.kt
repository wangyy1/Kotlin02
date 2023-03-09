package com.wyy.demo.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import kotlin.system.measureTimeMillis


/**
 * 组合多个流
 */

fun main() = runBlocking<Unit> {
//    testZip2()
    testCombine()
}

/**
 * Zip
 * 流拥有一个 zip 操作符用于组合两个流中 的相关值
 */
suspend fun testZip() {
    val nums = (1..3).asFlow()
    val strs = flowOf("one", "two", " three")
    nums
        .zip(strs) { a, b ->
            "$a -> $b"
        }
        .collect {
            println(it)
        }
}

/**
 * Zip
 * 流拥有一个 zip 操作符用于组合两个流中 的相关值
 */
suspend fun testZip2() {
    val nums = (1..3).asFlow().onEach {
        delay(300)
    }
    val strs = flowOf("one", "two", " three").onEach {
        delay(400)
    }
    nums
        .zip(strs) { a, b ->
            "$a -> $b"
        }
        .collect {
            println(it)
        }
}

/**
 * combine
 * 与 zip 不同的点在于，combine 会将 flowA 发射的 emit 与 flowB **最新发射 **的 emit 组合起来
 */
suspend fun testCombine() {
    val nums = (1..3).asFlow().onEach {
        delay(300)
    }
    val strs = flowOf("one", "two", " three").onEach {
        delay(400)
    }
    nums
        .combine(strs) { a, b ->
            "$a -> $b"
        }
        .collect {
            println(it)
        }
}

