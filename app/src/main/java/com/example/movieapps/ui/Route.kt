package com.example.movieapps.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapps.data.DataSource
import com.example.movieapps.ui.view.ListMovieView
import com.example.movieapps.ui.view.MovieDetailPreview
import com.example.movieapps.ui.view.MovieDetailView
import com.example.movieapps.ui.view.ProfileView
import com.example.movieapps.viewmodel.ListMovieUIState
import com.example.movieapps.viewmodel.ListMovieViewModel
import com.example.movieapps.viewmodel.MovieDetailUiState
import com.example.movieapps.viewmodel.MovieDetailViewModel

enum class ListScreen(){
    ListMovie,
    MovieDetail,
    Profile
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppsRoute(){
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold (

    ){
        NavHost(
            navController = navController,
            startDestination = ListScreen.ListMovie.name,
            modifier = Modifier.padding(it)
            ){
            composable(ListScreen.ListMovie.name){
                val listMovieViewModel: ListMovieViewModel = viewModel()
                when(val status = listMovieViewModel.listMovieUIState){
                    is ListMovieUIState.Loading-> {}
                    is ListMovieUIState.Success-> ListMovieView(movieList = status.data, onFavClicked = { listMovieViewModel.onFavClicked(it)}, onCardClicked = {navController.navigate(ListScreen.MovieDetail.name+"/"+it.id)})
                    is ListMovieUIState.Error-> {}
                    else -> {}
                }
            }
            composable(ListScreen.MovieDetail.name+"/{movieId}"){ it ->
                val movieDetailViewModel : MovieDetailViewModel = viewModel()
                movieDetailViewModel.getMovieById(it.arguments?.getString("movieId")!!.toInt())

                when(val status = movieDetailViewModel.movieDetailUiState){
                    is MovieDetailUiState.Success -> {
                        MovieDetailView(movie = status.data, onFavClicked = {})
                    }
                    is MovieDetailUiState.Loading -> {}
                    is MovieDetailUiState.Error -> {}
                }
            }
            composable(ListScreen.Profile.name){
                ProfileView()
            }
        }
    }
}