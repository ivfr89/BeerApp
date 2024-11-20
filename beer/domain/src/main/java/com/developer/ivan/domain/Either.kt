package com.developer.ivan.domain

import kotlinx.coroutines.flow.flow

/**
 * Port of https://github.com/scala/scala/blob/v2.12.1/src/library/scala/util/Either.scala
 *
 * Represents a value of one of two possible types (a disjoint union.)
 * An instance of Either is either an instance of [Left] or [Right].
 */
sealed class Either<out A, out B> {
    /**
     * Returns `true` if this is a [Right], `false` otherwise.
     * Used only for performance instead of fold.
     */
    internal abstract val isRight: Boolean

    /**
     * Returns `true` if this is a [Left], `false` otherwise.
     * Used only for performance instead of fold.
     */
    internal abstract val isLeft: Boolean

    fun isLeft(): Boolean = isLeft
    fun isRight(): Boolean = isRight

    /**
     * Applies `ifLeft` if this is a [Left] or `ifRight` if this is a [Right].
     *
     * Example:
     * ```
     * val result: Either<Exception, Value> = possiblyFailingOperation()
     * result.fold(
     *      { log("operation failed with $it") },
     *      { log("operation succeeded with $it") }
     * )
     * ```
     *
     * @param ifLeft the function to apply if this is a [Left]
     * @param ifRight the function to apply if this is a [Right]
     * @return the results of applying the function
     */
    inline fun <C> fold(ifLeft: (A) -> C, ifRight: (B) -> C): C = when (this) {
        is Right -> ifRight(b)
        is Left -> ifLeft(a)
    }

    suspend fun <C> suspendFold(ifLeft: suspend (A) -> C, ifRight: suspend (B) -> C): C = when (this) {
        is Right -> ifRight(b)
        is Left -> ifLeft(a)
    }

    /**
     * If this is a `Left`, then return the left value in `Right` or vice versa.
     *
     * Example:
     * ```
     * Left("left").swap()   // Result: Right("left")
     * Right("right").swap() // Result: Left("right")
     * ```
     */
    fun swap(): Either<B, A> = fold({ Right(it) }, { Left(it) })

    /**
     * The given function is applied if this is a `Right`.
     *
     * Example:
     * ```
     * Right(12).map { "flower" } // Result: Right("flower")
     * Left(12).map { "flower" }  // Result: Left(12)
     * ```
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <C> map(f: (B) -> C): Either<A, C> =
        fold({ Left(it) }, { Right(f(it)) })

    fun mapToEmpty(): Either<A, Unit> =
        map { }

    /**
     * The given function is applied if this is a `Left`.
     *
     * Example:
     * ```
     * Right(12).mapLeft { "flower" } // Result: Right(12)
     * Left(12).mapLeft { "flower" }  // Result: Left("flower)
     * ```
     */
    inline fun <C> mapLeft(f: (A) -> C): Either<C, B> =
        fold({ Left(f(it)) }, { Right(it) })

    /**
     * Map over Left and Right of this Either
     */
    inline fun <C, D> bimap(leftOperation: (A) -> C, rightOperation: (B) -> D): Either<C, D> =
        fold({ Left(leftOperation(it)) }, { Right(rightOperation(it)) })

    /**
     * Returns `false` if [Left] or returns the result of the application of
     * the given predicate to the [Right] value.
     *
     * Example:
     * ```
     * Right(12).exists { it > 10 } // Result: true
     * Right(7).exists { it > 10 }  // Result: false
     *
     * val left: Either<Int, Int> = Left(12)
     * left.exists { it > 10 }      // Result: false
     * ```
     */
    fun exists(predicate: (B) -> Boolean): Boolean =
        fold({ false }, { predicate(it) })

    /**
     * The left side of the disjoint union, as opposed to the [Right] side.
     */
    data class Left<out A>(val a: A) : Either<A, Nothing>() {
        override val isLeft
            get() = true
        override val isRight
            get() = false
    }

    /**
     * The right side of the disjoint union, as opposed to the [Left] side.
     */
    data class Right<out B>(val b: B) : Either<Nothing, B>() {
        override val isLeft
            get() = false
        override val isRight
            get() = true
    }
}

/**
 * Binds the given function across [Either.Right].
 *
 * @param f The function to bind across [Either.Right].
 */
inline fun <A, B, C> Either<A, B>.flatMap(f: (B) -> Either<A, C>): Either<A, C> {
    return when (this) {
        is Either.Right -> f(this.b)
        is Either.Left -> this
    }
}

/**
 * Binds the given function across [Either.Left].
 *
 * @param f The function to bind across [Either.Left].
 */
inline fun <A, B, C> Either<A, B>.flatMapLeft(f: (A) -> Either<C, B>): Either<C, B> {
    return when (this) {
        is Either.Right -> this
        is Either.Left -> f(this.a)
    }
}

/**
 * Returns `true` if this is a [Either.Right] and is equal to param, `false` otherwise.
 *
 * @param elem The objecto to compare to [Either.Right].
 */
fun <A, B> Either<A, B>.contains(elem: B): Boolean =
    this.fold({ false }, { it == elem })

/**
 * Returns the value from this [Either.Right] or the given argument if this is a [Either.Left].
 *
 * Example:
 * ```
 * Right(12).getOrElse(17) // Result: 12
 * Left(12).getOrElse(17)  // Result: 17
 * ```
 */
inline fun <B> Either<*, B>.getOrElse(default: () -> B): B =
    this.fold({ default() }, { it })

/**
 * Returns the value from this [Either.Right] or null if this is a [Either.Left].
 *
 * Example:
 * ```
 * Right(12).orNull() // Result: 12
 * Left(12).orNull()  // Result: null
 * ```
 */
fun <B> Either<*, B>.orNull(): B? = getOrElse { null }

/**
 * Wraps the caller into a Right side of an Either
 */
fun <T> T.toRight() = Either.Right(this)

/**
 * Wraps the caller into a Left side of an Either
 */
fun <T> T.toLeft() = Either.Left(this)

fun <L, R> Either<L, R>.asFlow() = flow {
    emit(this@asFlow)
}
