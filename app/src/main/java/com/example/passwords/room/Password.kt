package com.example.passwords.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Password")
data class Password(
    @ColumnInfo(name = "dateTime") val dateTime: String,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "site") val site: String?,
    @ColumnInfo(name = "descricao") val descricao: String?
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
