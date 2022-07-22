package com.example.koinpractice.network

class UserRepository(private val api: GithubApi) {
    fun getAllUsers() = api.getUsers()
}