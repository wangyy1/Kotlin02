package com.wyy.demo.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.lang.Exception


/**
 * 操作符
 */
fun main() = runBlocking<Unit> {
//    testMap()
//    testTransform()
//    testTake()
//    testReduce()
//    testFold()
//    testFirst()
//    testSingle()
//    testToList()
    testToSet()
}

/**
 * 过度流操作符
 */
suspend fun testMap(): Unit {
    suspend fun performRequest(request: Int): String {
        delay(1000) // 模仿长时间运行的异步工作
        return "response $request"
    }


    (1..3).asFlow()
        .map {
            performRequest(it)
        }
        .collect {
            println(it)
        }
}


/**
 * 转换操作符
 */
suspend fun testTransform(): Unit {
    suspend fun performRequest(request: Int): String {
        delay(1000) // 模仿长时间运行的异步工作
        return "response $request"
    }


    (1..3).asFlow()
        .transform {
            emit("Making request $it")
            emit(performRequest(it))
        }
        .collect {
            println(it)
        }
}

/**
 * 限长操作符,获取指定数量的数据
 */
suspend fun testTake() {
    fun numbers(): Flow<Int> = flow {
        try {
            emit(1)
            emit(2)
            println("This line will not execute")
            emit(3)
        } finally {
            println("Finally in numbers")
        }
    }

    numbers()
        .take(2)
        .collect {
            println(it)
        }
}

/**
 * 末端流操作符,将流规约到单个值
 */
suspend fun testReduce() {
    val sum = (1..5).asFlow()
        .map {
            it * it
        }
        .reduce { a, b ->
            a + b
        }
    println(sum)
}

/**
 * 末端流操作符,根据初始值将流规约到单个值
 */
suspend fun testFold() {
    val sum = (1..5).asFlow()
        .map {
            it * it
        }
        .fold(1) {acc, value ->
            acc + value
        }
    println(sum)
}

/**
 * 末端流操作符,获取第一个值
 */
suspend fun testFirst() {
    val sum = (1..5).asFlow()
        .map {
            it * it
        }
        .first()
    println(sum)
}

/**
 * 末端流操作符,确保流发射单个值
 */
suspend fun testSingle() {
    val sum = (1..5).asFlow()
        .map {
            it * it
        }
        .single()
    println(sum)
}
/**
 * 末端流操作符,toList
 */
suspend fun testToList() {
    val sum = (1..5).asFlow()
        .map {
            it * it
        }
        .toList()
    println(sum)
}

/**
 * 末端流操作符,toSet
 */
suspend fun testToSet() {
    val sum = (1..5).asFlow()
        .map {
            it * it
        }
        .toSet()
    println(sum)
}