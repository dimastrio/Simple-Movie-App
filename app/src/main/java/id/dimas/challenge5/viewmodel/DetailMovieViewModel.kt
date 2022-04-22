package id.dimas.challenge5.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.dimas.challenge5.model.DetailMovieItem
import id.dimas.challenge5.service.TMDBApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieViewModel(private val apiService: TMDBApiService) : ViewModel() {

    private val _movie = MutableLiveData<DetailMovieItem>()
    val movie: LiveData<DetailMovieItem> get() = _movie

    private val _dataError = MutableLiveData<String>()
    val dataError: LiveData<String> get() = _dataError

    fun getDetailMovie(movie_id: Int) {
        apiService.getDetailMovie(movie_id, "072af9268186d5a57f828c9f3dd51aac")
            .enqueue(object : Callback<DetailMovieItem> {
                override fun onResponse(
                    call: Call<DetailMovieItem>,
                    response: Response<DetailMovieItem>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            _movie.value = response.body()
                        } else {
                            _dataError.postValue("Data Kosong")
                        }
                    } else {
                        _dataError.postValue("Pengambilan Data Gagal")
                    }
                    Log.w("onResponse", "Berhasil")
                }

                override fun onFailure(call: Call<DetailMovieItem>, t: Throwable) {
                    Log.w("onFailure", "Gagal")
                }

            })
    }

}