package com.example.movieapps.model

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import java.text.SimpleDateFormat
import java.util.Date

data class Movie(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: Date,
    val title: String,
    val voteAverage: Float,
    val voteCount: Int,
    var isLiked: Boolean = false,
){
    @SuppressLint("SimpleDateFormat")
    fun getYear(): String{
        val df = SimpleDateFormat("yyyy")
        return df.format(releaseDate)
    }
}
