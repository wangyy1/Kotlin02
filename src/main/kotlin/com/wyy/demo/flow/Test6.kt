package com.wyy.demo.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.lang.Exception


/**
 * 流是连续的,流的每次单独收集都是按顺序执行的
 */
fun main() = runBlocking<Unit> {
    (1..5).asFlow()
        .filter {
            println("Filter $it")
            it % 2 == 0
        }
        .map {
            println("Map $it")
            "string $it"
        }
        .collect {
            println("Collect $it")
        }
}