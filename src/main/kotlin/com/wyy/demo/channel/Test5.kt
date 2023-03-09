package com.wyy.demo.channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/**
 * 扇入
 */
fun main() = runBlocking<Unit> {
    val channel = Channel<String>()
    launch {
        sendString(channel, "foo", 200L)
    }
    launch {
        sendString(channel, "BAR!", 500L)
    }
    repeat(6) {
        println(channel.receive())
    }
    coroutineContext.cancelChildren()
}

suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
    while (true) {
        delay(time)
        channel.send(s)
    }
}