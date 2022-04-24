package id.dimas.challenge5.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.dimas.challenge5.database.User
import id.dimas.challenge5.helper.SharedPref
import id.dimas.challenge5.helper.SharedPref.Companion.KEY_EMAIL
import id.dimas.challenge5.helper.UserRepo
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepo: UserRepo, private val sharedPref: SharedPref) :
    ViewModel() {

    private val _updateMessage = MutableLiveData<String>()
    val updateMessage: LiveData<String> get() = _updateMessage

    private var _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun updateToDb(user: User) {
        viewModelScope.launch {
            val result = userRepo.updateUser(user)
            if (result != 0) {
                userRepo.getUser(user.email)
                _updateMessage.value = "Update Berhasil"
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            val email = sharedPref.getEmail(KEY_EMAIL)
            _user.value = userRepo.getUser(email)
        }
    }


//    private fun getDataFromDb(email:String){
//        viewModelScope.launch {
//            val result = userRepo.getUser(email)
//            if (result != null){
//                _
//            }
//        }
//    }
}