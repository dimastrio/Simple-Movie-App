package id.dimas.challenge5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.dimas.challenge5.database.User
import id.dimas.challenge5.helper.SharedPref
import id.dimas.challenge5.helper.UserRepo
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepo: UserRepo, private val sharePref: SharedPref) :
    ViewModel() {

    private var _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private var _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user


    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private var _succesMessage = MutableLiveData<String>()
    val succesMessage: LiveData<String> get() = _succesMessage


    fun checkUserFromDb(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepo.checkRegisterUser(email)
            if (result != null) {
                if (result.email == email && result.password == password) {
                    _user.value = result
                    _succesMessage.value = "Login Berhasil"
                } else {
                    _errorMessage.value = "Username Atau Password Kamu Salah"
                }
//                    it.userId?.let { it1 ->
//                        sharePref.setData(
//                            it1,
//                            it.username,
//                            it.email,
//                            it.password
//                        )
//                    }
            } else {
                _errorMessage.value = "Akun Belum Terdaftar"
            }
        }
    }
}