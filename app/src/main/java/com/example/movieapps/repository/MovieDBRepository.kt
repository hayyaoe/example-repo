package com.example.movieapps.repository

import android.annotation.SuppressLint
import com.example.movieapps.model.Movie
import com.example.movieapps.service.MovieDBServices
import java.text.SimpleDateFormat

class MovieDBRepository (private val movieDBServices: MovieDBServices){
    @SuppressLint("SimpleDateFormat")
    suspend fun getAllMovie(page: Int = 1): List<Movie>{

        val listRawMovie = movieDBServices.getAllMovie(page).results
        val data = mutableListOf<Movie>()

        for (rawMovie in listRawMovie){
            val movie = Movie(
                rawMovie.id,
                rawMovie.overview,
                rawMovie.poster_path,
                SimpleDateFormat("yyyy-MM-dd").parse(rawMovie.release_date)!!,
                rawMovie.title,
                rawMovie.vote_average.toFloat(),
                rawMovie.vote_count
            )
            data.add(movie)
        }
        return data
    }

    @SuppressLint("SimpleDateFormat")
    suspend fun getMovieDetail(movieId: Int): Movie {
        val rawMovie = movieDBServices.getMovieDetail(movieId)

        return Movie(
            rawMovie.id,
            rawMovie.overview,
            rawMovie.poster_path,
            SimpleDateFormat("yyyy-MM-dd").parse(rawMovie.release_date)!!,
            rawMovie.title,
            rawMovie.vote_average.toFloat(),
            rawMovie.vote_count
        )
    }
}