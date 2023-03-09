package com.wyy.demo.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import kotlin.system.measureTimeMillis


/**
 * 异常
 */

fun main() = runBlocking<Unit> {
//    testError2()
//    testCatch()
//    testOnEach()
//    testFlowComplete()
//    testFlowComplete2()
//    testFlowComplete3()
//    testOnEach2()
//    testLaunchIn()
    testFlowCancel()
}

suspend fun testError1() {
    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i)
        }
    }

    try {
        simple().collect {
            println(it)
            check(it <= 1) { // 抛出异常后不再发出任何值
                "Collected $it"
            }
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}


suspend fun testError2() {
    fun simple(): Flow<String> = flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i)
        }
    }.map {
        check(it <= 1) {"Crashed on $it"}
        "string $it"
    }

    try {
        simple().collect {
            println(it)
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}

/**
 * catch 过渡操作符遵循异常透明性，仅捕获上游异常
 * 如果 collect { ... } 块(位于 catch 之下)抛出一个异常，那么异常会逃 逸
 */
suspend fun testCatch() {
    fun simple(): Flow<String> = flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i)
        }
    }.map {
        check(it <= 1) {"Crashed on $it"}
        "string $it"
    }

    simple()
        .catch {
            emit("Caught $it")
        }.collect {
            println(it)
        }
}

/**
 * OnEach 声明式捕获
 * 我们可以将 catch 操作符的声明性与处理所有异常的期望相结合，将 collect 操作符的代码块移 动到 onEach 中，并将其放到 catch 操作符之前。收集该流必须由调用无参的 collect() 来 触发
 */
suspend fun testOnEach() {
    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i)
        }
    }

    simple()
        .onEach {
            check(it <= 1) {"Collected $it"}
            println(it)
        }
        .catch {
            println("Caught $it")
        }.collect()
}

/**
 * 测试流完成后执行一个动作-命令式
 */
suspend fun testFlowComplete() {
    fun simple(): Flow<Int> = (1..3).asFlow()

    try {
        simple().collect {
            println(it)
        }
    } finally {
        println("Done")
    }
}

/**
 * 测试流完成后执行一个动作-声明式
 * onCompletion 异常参数,能观察到所有异常并且仅在上游流成功完成 (没有取消或失败)的情况下接收一个 null 异常
 */
suspend fun testFlowComplete2() {
    fun simple(): Flow<Int> = (1..3).asFlow()

    simple()
        .onCompletion {
            println("Done $it")
        }
        .collect {
            check(it <=1 ) {"Collected $it"}
            println(it)
        }
}

suspend fun testFlowComplete3() {
    fun simple(): Flow<Int> = flow {
        emit(1)
        throw RuntimeException()
    }

    simple()
        .onCompletion {cause ->
            if (cause != null) {
                println("Flow completed exceptionally")
            }
        }
        .catch {cause ->
            println("Caught exception")
        }
        .collect {
            println(it)
        }
}

/**
 * 启动流
 */
suspend fun testOnEach2() {
    fun events(): Flow<Int> = (1..3).asFlow().onEach {
        delay(100)
    }

    events()
        .onEach {
            println("Event: $it")
        }
        .collect()
    println("Done")
}

/**
 * launchIn
 * 使用 launchIn 替换 collect 我们可以在单独的 协程中启动流的收集，这样就可以立即继续进一步执行代码
 */
suspend fun testLaunchIn() = coroutineScope {
    fun events(): Flow<Int> = (1..3).asFlow().onEach {
        delay(100)
    }

    events()
        .onEach {
            println("Event: $it")
        }
        .launchIn(this) // 在单独的协程中执行流
    println("Done")
}

/**
 * 流取消检测
 */
suspend fun testFlowCancel() = coroutineScope {
    (1..5).asFlow().cancellable().collect {
        if (it == 3)  cancel()
        println(it)
    }
}