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
 * select 接收多个通道
 */
fun CoroutineScope.fizz() = produce<String> {
    while (true) {
        delay(300)
        send("Fizz")
    }
}
fun CoroutineScope.buzz() = produce<String> {
    while (true) {
        delay(300)
        send("Buzz")
    }
}

suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) {
    select<Unit> {
        fizz.onReceive {
            println("fizz - > '$it")
        }
        buzz.onReceive {
            println("buzz - > '$it")
        }
    }
}

fun main()  = runBlocking {
    val fizz = fizz()
    val buzz = fizz()
    repeat(7) {
        selectFizzBuzz(fizz, buzz)
    }
    coroutineContext.cancelChildren()
}