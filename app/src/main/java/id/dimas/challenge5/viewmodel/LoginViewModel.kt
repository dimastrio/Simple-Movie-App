package id.dimas.challenge5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.dimas.challenge5.helper.UserRepo
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepo: UserRepo) : ViewModel() {

    private var _userId = MutableLiveData<Int?>()
    val userId: LiveData<Int?> get() = _userId

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private var _succesMessage = MutableLiveData<String>()
    val succesMessage: LiveData<String> get() = _succesMessage


    fun checkUserFromDb(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepo.checkRegisterUser(email, password)
            if (!result.isNullOrEmpty()) {
                val userId = userRepo.getUserId(email)
                _userId.value = userId
                _succesMessage.value = "Login Berhasil"
            } else {
                _errorMessage.value = "Username Atau Password Kamu Salah"
            }
        }
    }
}