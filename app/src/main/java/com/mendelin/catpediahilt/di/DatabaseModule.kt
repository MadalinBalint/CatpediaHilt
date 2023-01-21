package com.mendelin.catpediahilt.di

import android.content.Context
import androidx.room.Room
import com.mendelin.catpediahilt.data.local.dao.CatsDao
import com.mendelin.catpediahilt.data.local.dao.CatsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideCatsDao(appDatabase: CatsDatabase): CatsDao {
        return appDatabase.catsDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): CatsDatabase {
        return Room.databaseBuilder(context, CatsDatabase::class.java, "CatsDB")
            .addMigrations(*CatsDatabase.dbMigrations)
            .build()
    }
}