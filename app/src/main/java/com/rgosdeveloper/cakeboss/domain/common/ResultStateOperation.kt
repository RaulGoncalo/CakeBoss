package com.rgosdeveloper.cakeboss.domain.common

sealed class ResultStateOperation<out T> {
    data class Success<out T>(val value: T) : ResultStateOperation<T>()
    data class Error(val exception: Exception) : ResultStateOperation<Nothing>()
    object Loading : ResultStateOperation<Nothing>()
}