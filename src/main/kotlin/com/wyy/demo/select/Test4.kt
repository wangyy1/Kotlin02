package com.wyy.demo.select

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random

/**
 * select 延迟值
 * 获取list中第一个返回的值
 */
fun CoroutineScope.asyncString(time: Int) = async {
    delay(time.toLong())
    "Waited for $time ms"
}

fun CoroutineScope.asyncStringList(): List<Deferred<String>> {
    val random = Random(3)
    return List(12) {
        asyncString(random.nextInt(10000))
    }

}

fun main()  = runBlocking {
    val list = asyncStringList()
    val result = select<String> {
        list.withIndex().forEach { (index, deferred) ->
            deferred.onAwait { answer ->
                "Deferred $index produce answer '$answer'"
            }
        }
    }
    println(result)
    val countActive = list.count {
        it.isActive
    }
    println("$countActive coroutines are still active")
}