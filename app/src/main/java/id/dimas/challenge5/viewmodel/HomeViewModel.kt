package id.dimas.challenge5.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.dimas.challenge5.adapter.MovieAdapter
import id.dimas.challenge5.helper.UserRepo
import id.dimas.challenge5.model.MovieResponse
import id.dimas.challenge5.service.TMDBApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val apiService: TMDBApiService, private val userRepo: UserRepo) :
    ViewModel() {

    private val _dataError = MutableLiveData<String>()
    val dataError: LiveData<String> get() = _dataError

    private val _movie = MutableLiveData<MovieResponse>()
    val movie: LiveData<MovieResponse> get() = _movie

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _movieBundle = MutableLiveData<Bundle>()
    val movieBundle: LiveData<Bundle> get() = _movieBundle

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

    fun getUsername(email: String?) {
        viewModelScope.launch {
            val result = userRepo.getUsername(email)
            _username.value = "Welcome, $result!"
        }
    }

    fun action(movie_id: Int) {
        val mBundle = Bundle()
        mBundle.putInt(KEY_MOVIE_ID, movie_id)
        _movieBundle.value = mBundle

    }

    companion object {
        const val KEY_MOVIE_ID = "movie_id"
    }

}