package com.example.retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.retrofit2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface JokesInterface{
    @GET("Programming?blacklistFlags=nsfw")
    suspend fun getProgrammingJoke(): Jokes
}

private const val BASE_URL =
    "https://v2.jokeapi.dev/joke/"

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).build()

    private val jokesAPI = retrofit.create(JokesInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        generateJoke()
        binding.buttonGenerateJoke.setOnClickListener {
            generateJoke()
        }
    }

    private fun generateJoke(){
        lifecycleScope.launch {
            try {
                val jokesList = jokesAPI.getProgrammingJoke()
                if (jokesList.joke == null){generateJoke()}
                binding.jokeText.text = jokesList.joke
            } catch (e: java.lang.Exception){
                Snackbar.make(
                    findViewById(R.id.main_view),
                    "There was an error",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Retry") { generateJoke() }.show()
            }
        }
    }

}