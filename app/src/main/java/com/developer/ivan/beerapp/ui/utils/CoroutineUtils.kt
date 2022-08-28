package com.developer.ivan.beerapp.ui.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.CoroutineContext

interface CoroutineUtils {

    val coroutineContext: CoroutineContext

    suspend fun <T> T.postOn(stateFlow: MutableStateFlow<T>) {
        withContext(coroutineContext) {
            stateFlow.value = this@postOn
        }
    }

    suspend fun <T> T.postOn(sharedFlow: MutableSharedFlow<T>) {
        withContext(coroutineContext) {
            sharedFlow.emit(this@postOn)
        }
    }


    fun <T> CoroutineScope.launchAndPost(
        stateFlow: MutableStateFlow<T>,
        context: CoroutineContext = coroutineContext,
        block: suspend CoroutineScope.() -> T
    ) {
        launch(context = context) {
            block().postOn(stateFlow)
        }
    }

    fun <T> CoroutineScope.launchAndPost(
        sharedFlow: MutableSharedFlow<T>,
        context: CoroutineContext = coroutineContext,
        block: suspend CoroutineScope.() -> T
    ) {
        launch(context = context) {
            block().postOn(sharedFlow)
        }
    }
}

class CoroutineManageContext(override val coroutineContext: CoroutineContext) : CoroutineUtils
