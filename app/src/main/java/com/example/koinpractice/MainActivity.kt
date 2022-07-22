package com.example.koinpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koinpractice.databinding.ActivityMainBinding
import com.example.koinpractice.model.LoadingState
import com.example.koinpractice.model.Status
import com.example.koinpractice.view.UserAdapter
import com.example.koinpractice.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    // by viewModel() will create our ViewModel for use
    private val userViewModel by viewModel<UserViewModel>()

//    private val viewModel by lazy {
//        ViewModelProvider(....)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureObservers()
    }

    private fun configureObservers() {
        userViewModel.data.observe(this, {
            binding.rvUserList.adapter = UserAdapter(it)
            binding.rvUserList.layoutManager = GridLayoutManager(
                this,
                3,
                GridLayoutManager.VERTICAL,
                false
            )
        })

        userViewModel.loadingState.observe(this, {
            when(it) {
                LoadingState.LOADING -> binding.progressBar.visibility = View.VISIBLE
                LoadingState.LOADED -> binding.progressBar.visibility = View.GONE
            }
        })
    }

}