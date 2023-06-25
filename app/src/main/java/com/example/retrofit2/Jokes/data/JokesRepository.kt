package com.example.retrofit2.Jokes.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL =
    "https://v2.jokeapi.dev/"

class JokesRepository {
    private val jokesInterface: JokesInterface
    private val logging = HttpLoggingInterceptor()
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

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

    suspend fun getJokes() = jokesInterface.getProgrammingJoke()
}