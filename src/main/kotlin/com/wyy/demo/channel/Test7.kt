package com.wyy.demo.channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/**
 * 通道是公平的
 */
data class Ball(var hits: Int)

fun main() = runBlocking<Unit> {
    val table = Channel<Ball>()
    launch {
        player("乒", table)
    }
    launch {
        player("乓", table)
    }
    table.send(Ball(0))

    delay(1000)
    coroutineContext.cancelChildren()

}

suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) {
        ball.hits++
        println("$name $ball")
        delay(300)
        table.send(ball)
    }
}