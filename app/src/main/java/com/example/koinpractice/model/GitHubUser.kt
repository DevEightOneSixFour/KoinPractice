package com.example.koinpractice.model

import com.google.gson.annotations.SerializedName

data class GitHubUser(
    val id: Long,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)