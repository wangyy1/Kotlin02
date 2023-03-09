package com.wyy.demo.coroutine

import jdk.nashorn.internal.objects.Global
import kotlinx.coroutines.*


/**
 * 全局捕获异常
 */
fun main() = runBlocking<Unit>() {
     val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
         println("CoroutineExceptionHandler got $throwable")
     }

    val job = GlobalScope.launch(handler) {
        throw AssertionError()
    }

    val deferred = GlobalScope.async(handler) { // 同样是根协程，但使用 async 代替了 launch
        throw ArithmeticException() // 没有打印任何东西，依赖用户去调用 deferred.await()
    }
    joinAll(job, deferred)
}


