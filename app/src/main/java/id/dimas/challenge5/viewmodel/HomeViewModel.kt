package id.dimas.challenge5.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.dimas.challenge5.adapter.MovieAdapter
import id.dimas.challenge5.model.MovieResponse
import id.dimas.challenge5.service.TMDBApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val apiService: TMDBApiService) : ViewModel() {

    private val _dataError = MutableLiveData<String>()
    val dataError: LiveData<String> get() = _dataError

    private val _movie = MutableLiveData<MovieResponse>()
    val movie: LiveData<MovieResponse> get() = _movie

    private lateinit var movieAdapter: MovieAdapter

    fun getAllMovies() {
        apiService.getAllMovie("072af9268186d5a57f828c9f3dd51aac")
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
//                        movieAdapter = MovieAdapter()
                            _movie.value = response.body()
//                            movieAdapter.updateData(it)
                        } else {
                            _dataError.postValue("Data Kosong")
                        }
                    } else {
                        _dataError.postValue("Pengambilan Data Gagal")
                    }
                    Log.w("onResponse", "Berhasil")
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    _dataError.postValue("Server Bermasalah")
                    Log.w("onFailure", "Gagal")
                }

            })
    }
}