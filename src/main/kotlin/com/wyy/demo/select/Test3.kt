package com.wyy.demo.select

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import kotlin.concurrent.fixedRateTimer

/**
 * select 发送
 */
fun CoroutineScope.produceNumbers(side: SendChannel<Int>) = produce<Int> {
    for (num in 1..10) {
        delay(100)
        select<Unit> {
            onSend(num){

            }
            side.onSend(num) {

            }
        }
    }
}

fun main()  = runBlocking {
    val side = Channel<Int>() // 分配side 通道
    launch { // 对于side 通道来说，这是一个很快的消费者
        side.consumeEach {
            println("Side channel has $it")
        }
    }
    produceNumbers(side).consumeEach {
        println("Consuming $it")
        delay(250) // 不要着急，让我们正确消化消耗被发送来的数字
    }
    println("Done consuming")
    coroutineContext.cancelChildren()
}