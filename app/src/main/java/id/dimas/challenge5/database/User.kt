package id.dimas.challenge5.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int?,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "fullname") val fullname: String,
    @ColumnInfo(name = "datebirth") val datebirth: String,
    @ColumnInfo(name = "address") val address: String
)
