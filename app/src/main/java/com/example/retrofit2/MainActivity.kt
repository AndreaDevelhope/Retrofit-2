package com.example.retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.retrofit2.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonGenerateJoke.setOnClickListener {
            viewModel.getJokes()
        }
        viewModel.jokesLiveData.observe(this) {
            showJoke(it)
        }
    }


    private fun showJoke(jokesList: Jokes) {
        when (jokesList.type) {
            "single" -> {
                binding.jokeText.text = jokesList.joke
                binding.buttonGenerateJoke2.isEnabled = false
            }
            "twopart" -> {
                binding.jokeText.text = jokesList.setup
                binding.buttonGenerateJoke2.isEnabled = true
                binding.buttonGenerateJoke2.setOnClickListener {
                    binding.jokeText.text = jokesList.delivery
                    binding.buttonGenerateJoke2.isEnabled = false
                }
            }
            else -> viewModel.getJokes()
        }
    }

}