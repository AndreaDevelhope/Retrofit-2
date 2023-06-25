package com.example.retrofit2.Jokes.data

import retrofit2.http.GET

interface JokesInterface {
    @GET("joke/Programming?blacklistFlags=nsfw")
    suspend fun getProgrammingJoke(): JokesRemote
}