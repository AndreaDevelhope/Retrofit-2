package com.example.retrofit2

import retrofit2.http.GET

interface JokesInterface {
    @GET("joke/Programming?blacklistFlags=nsfw")
    suspend fun getProgrammingJoke(): Jokes
}