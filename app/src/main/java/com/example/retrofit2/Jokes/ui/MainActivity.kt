package com.example.retrofit2.Jokes.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        binding.buttonGenerateJoke2.setOnClickListener {
            binding.jokeSecond.visibility = View.VISIBLE
            binding.buttonGenerateJoke2.isEnabled = false
        }
        viewModel.jokesLiveData.observe(this) {
            binding.jokeText.text = it.firstPart
            showTwoPartJoke(it.secondPart)
        }
    }

    private fun showTwoPartJoke(secondPart: String?) {
        binding.jokeSecond.visibility = View.GONE
        if (secondPart != null) {
            binding.jokeSecond.text = secondPart
            binding.buttonGenerateJoke2.isEnabled = true
        } else {
            binding.buttonGenerateJoke2.isEnabled = false
        }
    }

}