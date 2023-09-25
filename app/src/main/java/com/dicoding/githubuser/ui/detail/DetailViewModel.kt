package com.dicoding.githubuser.ui.detail

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

class DetailViewModel : ViewModel() {

    val resultDetailUser = MutableLiveData<Output>()
    val resultFollowers = MutableLiveData<Output>()
    val resultFollowing = MutableLiveData<Output>()

    fun getUserDetail(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .userGitService
                    .getUser(username)

                emit(response)
            }.onStart {
                resultDetailUser.value= Output.Loading(true)
            }.onCompletion {
                resultDetailUser.value= Output.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultDetailUser.value = Output.Error(it)
            }.collect {
                resultDetailUser.value  = Output.Success(it)
            }
        }
    }

    fun getFollower(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .userGitService
                    .getFollowers(username)

                emit(response)
            }.onStart {
                resultFollowers.value= Output.Loading(true)
            }.onCompletion {
                resultFollowers.value= Output.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowers.value = Output.Error(it)
            }.collect {
                resultFollowers.value  = Output.Success(it)
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .userGitService
                    .getFollowings(username)

                emit(response)
            }.onStart {
                resultFollowing.value= Output.Loading(true)
            }.onCompletion {
                resultFollowing.value= Output.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowing.value = Output.Error(it)
            }.collect {
                resultFollowing.value  = Output.Success(it)
            }
        }
    }
}