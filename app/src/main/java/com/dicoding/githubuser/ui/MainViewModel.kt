package com.dicoding.githubuser.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuser.data.remote.ApiConfig
import com.dicoding.githubuser.utils.Output
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val resultUser = MutableLiveData<Output>()

    fun getUserList() {
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .userGitService
                    .getUserSearch()

                emit(response)
            }.onStart {
                resultUser.value= Output.Loading(true)
            }.onCompletion {
                resultUser.value= Output.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultUser.value = Output.Error(it)
            }.collect {
                resultUser.value  = Output.Success(it)
            }
        }
    }

    fun getSearchUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .userGitService
                    .searchGitUser(
                        mapOf(
                            "q" to username
                        )
                    )

                emit(response)
            }.onStart {
                resultUser.value= Output.Loading(true)
            }.onCompletion {
                resultUser.value= Output.Loading(false)
            }.catch {
                it.printStackTrace()
                resultUser.value = Output.Error(it)
            }.collect {
                resultUser.value  = Output.Success(it.items)
            }
        }
    }
}