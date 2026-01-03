package com.player.domain.util

sealed class Resource<Type>(
    val data: Type? = null,
    val error: Throwable? = null
) {
    data class Success<Type>(val d: Type) : Resource<Type>(d)
    data class Error<Type>(val err: Throwable, val d: Type? = null) : Resource<Type>(d, err)
    data class Loading<Type>(val d: Type? = null) : Resource<Type>(d)
}

