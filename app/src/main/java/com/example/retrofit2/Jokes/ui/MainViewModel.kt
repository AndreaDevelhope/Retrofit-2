package com.example.retrofit2.Jokes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofit2.Jokes.data.JokesRepository
import com.example.retrofit2.Jokes.ui.Jokes.JokeUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    private val repository = JokesRepository()

    private val _jokesLiveData = MutableLiveData<JokeUiModel>()
    val jokesLiveData: LiveData<JokeUiModel> = _jokesLiveData


    fun getJokes() {
        CoroutineScope(Dispatchers.IO).launch {
            val jokeList = repository.getJokes()
            val jokes = JokeUiModel("", null)
            when (jokeList.type) {
                "single" -> {
                    jokes.firstPart = jokeList.joke.toString()
                }
                "twopart" -> {
                    jokes.firstPart = jokeList.setup.toString()
                    jokes.secondPart = jokeList.delivery.toString()
                }
            }
            _jokesLiveData.postValue(jokes)
        }
    }
}