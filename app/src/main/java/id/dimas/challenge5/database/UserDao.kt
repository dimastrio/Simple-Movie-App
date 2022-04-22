package id.dimas.challenge5.database

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE email =:email AND password =:password")
    fun checkRegisterUser(email: String, password: String): List<User>

    @Query("SELECT email FROM User WHERE email = :email")
    fun checkEmailUser(email: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Query("SELECT username FROM User WHERE email =:email")
    fun getUsername(email: String?): String

    @Query("SELECT userId FROM User WHERE email =:email")
    fun getUserId(email: String): Int?

    @Update
    fun updateUser(user: User): Int

}