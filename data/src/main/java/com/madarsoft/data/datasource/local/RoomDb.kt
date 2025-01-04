package com.madarsoft.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madarsoft.data.datasource.local.UserDao
import com.madarsoft.data.model.User


@Database(entities = [User::class], version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun userDao(): UserDao
}