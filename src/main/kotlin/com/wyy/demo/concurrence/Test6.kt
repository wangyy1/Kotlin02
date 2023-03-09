package com.wyy.demo.concurrence

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * 解决办法，Actor
 * actor本身就是一个协程，内部包含一个channel，通过channel与其他协程进行通信。要注意actor内部是从channel接收信息的
 */

var counter6 = 0

sealed class CounterMsg
object IncCounter: CounterMsg()
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()


fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter = 0
    for (msg in channel) {
        when(msg) {
            is IncCounter -> counter++
            is GetCounter -> msg.response.complete(counter)
        }
    }
}


fun main() = runBlocking<Unit> {
    val counter = counterActor()
    withContext(Dispatchers.Default) {
        massiveRun {
            counter.send(IncCounter)
        }
    }
    val response = CompletableDeferred<Int>()
    counter.send(GetCounter(response))
    println("Counter= ${response.await()}")
    counter.close()
}