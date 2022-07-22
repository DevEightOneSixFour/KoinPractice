package com.example.koinpractice.di

import com.example.koinpractice.network.GithubApi
import com.example.koinpractice.network.UserRepository
import com.example.koinpractice.viewmodel.UserViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    // used just for injecting ViewModels
    viewModel {
        UserViewModel(get())
    }
}

val repositoryModule = module {
    // single == @Singleton
    single {
        UserRepository(get())
    }
}

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit) = retrofit.create(GithubApi::class.java)

    single { provideUseApi(get()) }
}

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpClient() = OkHttpClient.Builder().build()

    fun provideRetrofit(factory: Gson, client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
}

/*
    In the code above, we use several koin function to manage
    the different dependencies of our application

    1. viewModel: declare a ViewModel component and bind it to an Android Component lifecycle
    2. get(): In our case the UserViewModel class needs an instance of UserRepository as a parameter
        so we use the get() to tell koin to retrieve it for us. This will only work if there
        is a module that provides this dependency. Otherwise Koin will not find the dependency and
        the app will crash
    3. single: tell to Koin to create a singleton, the instance will be created only once during
        the execution of the application
    4. factory: creates a new instance of this dependency each time it is asked for
 */