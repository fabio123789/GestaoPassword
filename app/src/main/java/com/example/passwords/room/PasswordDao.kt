package com.example.passwords.room

import androidx.room.*

@Dao
interface PasswordDao {
    @Query("SELECT * FROM Password")
    fun getAllPasswords() : List<Password>

    @Query("SELECT * FROM Password WHERE id=:id ")
    fun getOnePassword(id: Int): Password

    @Insert
    fun insertPassword(password: Password)

    @Update
    fun updatePassword(password: Password)

    @Delete
    fun deletePassword(password: Password)
}
