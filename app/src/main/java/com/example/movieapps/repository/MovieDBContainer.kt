package com.example.movieapps.repository

import com.example.movieapps.service.MovieDBServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor(private val bearerToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $bearerToken")
            .build()
        return chain.proceed(request)
    }
}
class MovieDBContainer {

    companion object{
        val BASE_IMAGE = "https://image.tmdb.org/t/p/w500/"
    }

    private val BASE_URL = "https://api.themoviedb.org/3/movie/"
    private val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNjMxMDRjNGMwOWJiN2RhN2I5MDZiMTIzYTU1YTg0MiIsInN1YiI6IjY1MzZlZWI0N2ZjYWIzMDEyYzIyOGFiYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.p5XqKHoXPa5z-ah6Z3P1qGeX1SFdBOj_RtxwtxYW4-o"
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(ACCESS_TOKEN))
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    private val retrofitService: MovieDBServices by lazy{
        retrofit.create(MovieDBServices::class.java)
    }

    val movieDBRepositories: MovieDBRepository by lazy{
        MovieDBRepository(retrofitService)
    }
}
