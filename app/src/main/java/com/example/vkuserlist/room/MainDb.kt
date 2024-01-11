package com.example.vkuserlist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserInfo::class], version = 1)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): Dao
    companion object {
        fun getInstance(context: Context): MainDb {
            return Room.databaseBuilder(context.applicationContext,
                MainDb::class.java,
                "users.db").build()
        }
    }
}