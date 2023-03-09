package com.wyy.demo.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull


/**
 * 流构建器
 */
fun main() = runBlocking<Unit> {
    (1..3).asFlow().collect{
        println(it)
    }
}