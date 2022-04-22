package id.dimas.challenge5.helper

import android.content.Context
import id.dimas.challenge5.database.MovieDatabase
import id.dimas.challenge5.database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepo(context: Context) {

    private val mDb = MovieDatabase.getInstance(context)

    suspend fun updateUser(user: User) = withContext(Dispatchers.IO) {
        mDb?.userDao()?.updateUser(user)
    }

    suspend fun checkRegisterUser(email: String, password: String) = withContext(Dispatchers.IO) {
        mDb?.userDao()?.checkRegisterUser(email, password)
    }

    suspend fun insertUser(user: User) = withContext(Dispatchers.IO) {
        mDb?.userDao()?.insertUser(user)
    }

    suspend fun checkEmailUser(email: String) = withContext(Dispatchers.IO) {
        mDb?.userDao()?.checkEmailUser(email)
    }

    suspend fun getUsername(email: String?) = withContext(Dispatchers.IO) {
        mDb?.userDao()?.getUsername(email)
    }
}