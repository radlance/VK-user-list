package com.example.vkuserlist.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserInfo (
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("last_name") var lastName: String
)