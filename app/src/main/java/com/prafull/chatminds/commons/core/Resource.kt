package com.prafull.chatminds.commons.core

sealed interface Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>
    data class Error<out T>(val message: T) : Resource<T>
    data object Loading : Resource<Nothing>
}