package com.developer.ivan.domain

sealed class Failure {

    data class ElementNotFound(val reason: String?) : Failure()
    abstract class CustomFailure : Failure()
    object NullResult : CustomFailure()
}
