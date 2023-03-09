package com.wyy.demo.select

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlin.concurrent.fixedRateTimer

/**
 * select onReceiveCatching
 * select 中的 onReceive 子句在已经关闭的通道执行会发生失败，并导致相应的 select 抛出异 常。我们可以使用 onReceiveCatching 子句在关闭通道时执行特定操作
 */
suspend fun selectAorB(a: ReceiveChannel<String>, b: ReceiveChannel<String>) : String {
   return select<String> {
        a.onReceiveCatching {
            val value = it.getOrNull()
            if (value == null) {
                "Channel 'a' is closed"
            } else {
                "a -> $value"
            }
        }
        b.onReceiveCatching {
            val value = it.getOrNull()
            if (value == null) {
                "Channel 'b' is closed"
            } else {
                "b -> $value"
            }
        }
    }
}

fun main()  = runBlocking {

    val  a = produce {
        repeat(4) {
            send("Hello $it")
        }
    }

    val  b = produce {
        repeat(4) {
            send("World $it")
        }
    }

    repeat(8) {
        println(selectAorB(a, b))
    }
    coroutineContext.cancelChildren()
}