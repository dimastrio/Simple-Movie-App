package id.dimas.challenge5.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.dimas.challenge5.helper.UserRepo
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepo: UserRepo) : ViewModel() {


    fun checkUserFromDb(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepo.checkRegisterUser(email, password)
            if (!result.isNullOrEmpty()) {
                val userId = userRepo.getUserId(email)
            }
        }
    }
}