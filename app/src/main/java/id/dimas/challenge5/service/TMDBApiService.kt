package id.dimas.challenge5.service

import id.dimas.challenge5.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiService {

    @GET("movie/popular")
    fun getAllMovie(@Query("api_key") key: String): Call<MovieResponse>


}