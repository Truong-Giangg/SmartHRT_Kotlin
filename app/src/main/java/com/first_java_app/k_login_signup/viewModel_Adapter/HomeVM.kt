package com.first_java_app.k_login_signup.viewModel_Adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.first_java_app.k_login_signup.model.Movie
import com.first_java_app.k_login_signup.services.MovieRestClient
import kotlinx.coroutines.launch

class HomeVM : ViewModel() {
    private var _movieData: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    val movieData: LiveData<List<Movie>>
        get() = _movieData

    private var _errEvent: MutableLiveData<String> = MutableLiveData<String>()
    val errEvent: LiveData<String>
        get() = _errEvent

    fun getNowPlaying() {
        viewModelScope.launch {
            try {
                val movieResp = MovieRestClient.getInstance().api.listNowPlayMovies(
                    language = "vi-VN",
                    page = 1,
                )
                _movieData.postValue(movieResp.results!!)
            } catch (e: Exception) {
                _errEvent.value = e.message
            }
        }
    }

    fun getTopRatedMovie() {
        viewModelScope.launch {
            val movieResp = MovieRestClient.getInstance().api.listTopRatedMovies(
                language = "en-US",
                page = 1,
            )
            _movieData.postValue(movieResp.results!!)
        }
    }
}