package com.example.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.dao.UserDao
import com.example.myapplication.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class RaktaVahiniDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
