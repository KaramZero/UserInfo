package com.madarsoft.data.di

import android.content.Context
import androidx.room.Room
import com.madarsoft.data.datasource.local.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RoomDb::class.java,
        "User"
    ).build()


    @Singleton
    @Provides
    fun provideUserDao(db: RoomDb) = db.userDao()

}