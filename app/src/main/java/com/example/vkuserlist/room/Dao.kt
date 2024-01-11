package com.example.vkuserlist.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert
    suspend fun insertUser(userInfo: UserInfo)

    @Query("SELECT * FROM users")
    fun getInitials():Flow<List<UserInfo>>
}