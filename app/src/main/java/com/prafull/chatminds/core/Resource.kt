package com.prafull.chatminds.core

sealed interface Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>
    data class Error(val exception: Exception) : Resource<Nothing>
    data object Loading : Resource<Nothing>
}