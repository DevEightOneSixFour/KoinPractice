package com.example.koinpractice.network

import com.example.koinpractice.model.GitHubUser
import retrofit2.Call
import retrofit2.http.GET

interface GithubApi {

    @GET("users")
    fun getUsers(): Call<List<GitHubUser>>
}