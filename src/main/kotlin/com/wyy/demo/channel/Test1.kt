package com.wyy.demo.channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 通道
 * 延期的值提供了一种便捷的方法使单个值在多个协程之间进行相互传输。 通道提供了一种在 流中传输值的方法。
 */
fun main() = runBlocking {
//    test1()
    testChannelClose()
}


suspend fun test1() = coroutineScope {
    val channel = Channel<Int>()

    launch {
        for (x in 1..5) {
            println("send $x")
            channel.send(x * x)
        }
    }

    repeat(5) {
        println(channel.receive())
    }
    println("Done!")
}

/**
 * 通道关闭，调用close后保证先前发送出去的元素在通道关闭前被接收到
 */
suspend fun testChannelClose() = coroutineScope {
    val channel = Channel<Int>()

    launch {
        for (x in 1..5) channel.send(x * x)

        channel.close() // 关闭通道
    }

    for (y in channel) {
        println(y)
    }
    println("Done!")
}