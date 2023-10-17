package com.example.movieapps.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapps.data.DataSource
import com.example.movieapps.model.Movie
import kotlinx.coroutines.launch

sealed interface ListMovieUIState{
    data class Success(val data: List<Movie>):ListMovieUIState
    object Error: ListMovieUIState
    object Loading: ListMovieUIState
}
class ListMovieViewModel: ViewModel() {
    var listMovieUIState: ListMovieUIState by mutableStateOf(ListMovieUIState.Loading)

    private lateinit var sex: List<Movie>

    init {
        loadData()
    }
    private fun loadData(){
        viewModelScope.launch {
            try {
                sex = DataSource().loadMovie()
                listMovieUIState = ListMovieUIState.Success(sex)
            }catch (e: Exception){
                listMovieUIState = ListMovieUIState.Error
            }
        }
    }

    fun onFavClicked(movie: Movie){
        movie.isLiked = !movie.isLiked
//        send data to server
    }
}