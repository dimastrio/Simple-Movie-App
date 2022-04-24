package id.dimas.challenge5.database

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE email =:email")
    fun checkRegisterUser(email: String): User

    @Query("SELECT email FROM User WHERE email = :email")
    fun checkEmailUser(email: String): String

    @Query("SELECT * FROM User WHERE email =:email")
    fun getUser(email: String): User

    @Query("SELECT * FROM User WHERE email =:email")
    fun getUserData(email: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Query("SELECT username FROM User WHERE email =:email")
    fun getUsername(email: String): String

    @Query("SELECT userId FROM User WHERE email =:email")
    fun getUserId(email: String): Int

    @Update
    fun updateUser(user: User): Int

}