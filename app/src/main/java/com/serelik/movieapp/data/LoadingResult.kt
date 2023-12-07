package com.serelik.movieapp.data

sealed interface LoadingResults<out T> {
    object Loading : LoadingResults<Nothing>
    class Success<T>(val dataInfo: T) : LoadingResults<T>
    class Error(val e: Throwable) : LoadingResults<Nothing>
}
