package com.wyy.demo.channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/**
 * 带缓冲的通道
 */
fun main() = runBlocking<Unit> {
    val channel = Channel<Int>(4)
    val sender = launch {
        repeat(10) {
            println("Sending $it")
            channel.send(it)
        }
    }
    delay(1000)
    sender.children
}