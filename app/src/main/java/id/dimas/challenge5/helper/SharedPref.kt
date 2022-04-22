package id.dimas.challenge5.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    private var sharedPref: SharedPreferences = context.getSharedPreferences(ID_PREF, 0)
    private var editor = sharedPref.edit()

    fun setLogin(isLogin: Boolean) {
        editor.apply {
            putBoolean(IS_LOGIN, isLogin)
            apply()
        }
    }

    fun setData(email: String, id: Int) {
        editor.apply {
            putString(KEY_EMAIL, email)
            putInt(KEY_ID, id)
            putBoolean(IS_LOGIN, true)
            apply()
        }
    }

    fun getUserId(): Int {
        return sharedPref.getInt(KEY_ID, 0)
    }

    fun setEmail(email: String) {
        editor.apply {
            putString(KEY_EMAIL, email)
            apply()
        }
    }

    fun getEmail(email: String): String? {
        return sharedPref.getString(KEY_EMAIL, email)
    }

    fun isLogin(): Boolean {
        return sharedPref.getBoolean(IS_LOGIN, false)
    }

    fun getUsername(key: String, value: String): String? {
        return sharedPref.getString(key, value)
    }

    fun clearPref() {
        editor.apply {
            clear()
            apply()
        }
    }


    companion object {
        const val ID_PREF = "pref_id"
        const val IS_LOGIN = "is_login"
        const val KEY_EMAIL = "email"
        const val KEY_PASS = "password"
        const val KEY_USERNAME = "username"
        const val KEY_ID = "id"
    }

}