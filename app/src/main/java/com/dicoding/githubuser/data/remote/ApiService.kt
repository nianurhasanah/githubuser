package com.dicoding.githubuser.data.remote

import com.dicoding.githubuser.BuildConfig
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.response.UserGitResponse
import com.dicoding.githubuser.data.response.Users
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun getUserSearch(
        @Header("Authorization")
        authorization : String = BuildConfig.TOKEN
    ): MutableList<Users>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username")
        username: String,
        @Header("Authorization")
        authorization : String = BuildConfig.TOKEN,
    ): DetailUserResponse

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username")
        username: String,
        @Header("Authorization")
        authorization : String = BuildConfig.TOKEN
    ): MutableList<Users>

    @JvmSuppressWildcards
    @GET("users/{username}/follow")
    suspend fun getFollowings(
        @Path("username")
        username: String,
        @Header("Authorization")
        authorization : String = BuildConfig.TOKEN,
    ): MutableList<Users>

    @JvmSuppressWildcards
    @GET("search/user")
    suspend fun searchGitUser(
        @QueryMap
        params: Map<String, Any>,
        @Header("Authorization")
        authorization : String = BuildConfig.TOKEN,
    ): UserGitResponse
}