package com.example.koinpractice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koinpractice.model.GitHubUser
import com.example.koinpractice.model.LoadingState
import com.example.koinpractice.network.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val repo: UserRepository): ViewModel(), Callback<List<GitHubUser>> {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _data = MutableLiveData<List<GitHubUser>>()
    val data: LiveData<List<GitHubUser>>
        get() = _data

    init {
        fetchData()
    }

    private fun fetchData() {
        _loadingState.postValue(LoadingState.LOADING)
        repo.getAllUsers().enqueue(this)
    }


    override fun onResponse(call: Call<List<GitHubUser>>, response: Response<List<GitHubUser>>) {
        if (response.isSuccessful) {
            _data.postValue(response.body())
            _loadingState.postValue(LoadingState.LOADED)
        } else {
            _loadingState.postValue(LoadingState.error(response.errorBody().toString()))
        }
    }

    override fun onFailure(call: Call<List<GitHubUser>>, t: Throwable) {
        _loadingState.postValue(LoadingState.error(t.message))
    }
}