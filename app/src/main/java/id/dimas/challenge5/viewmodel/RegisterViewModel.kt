package id.dimas.challenge5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.dimas.challenge5.database.User
import id.dimas.challenge5.helper.UserRepo
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepo: UserRepo) : ViewModel() {

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> get() = _successMessage

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    fun saveUserToDb(username: String, email: String, password: String) {
        val user = User(null, username, email, password)
        viewModelScope.launch {
            val emails = userRepo.checkEmailUser(email)
            if (emails == null) {
                val result = userRepo.insertUser(user)
                if (result != 0L) {
                    _successMessage.value = "Register Sukses"
                }
            } else {
                _errorMessage.value = "Email Sudah Terdaftar"
            }
        }
    }
}