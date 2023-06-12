package com.example.retrofit2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL =
    "https://v2.jokeapi.dev/"

class MainViewModel : ViewModel() {
    private val jokesInterface: JokesInterface
    private val logging = HttpLoggingInterceptor()
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    private val _jokesLiveData = MutableLiveData<Jokes>()
    val jokesLiveData: LiveData<Jokes> = _jokesLiveData

    init {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        jokesInterface = retrofit.create(JokesInterface::class.java)
    }

    fun getJokes() {
        CoroutineScope(Dispatchers.IO).launch {
            val jokeList = jokesInterface.getProgrammingJoke()
            _jokesLiveData.postValue(jokeList)
        }
    }

}