package com.dicoding.githubuser.utils

sealed class Output {
    data class Success<out T>(val data: T) : Output()
    data class Error(val exception: Throwable) : Output()
    data class Loading(val isLoading: Boolean) : Output()

}
