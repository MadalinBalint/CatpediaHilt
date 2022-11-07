package com.mendelin.catpediahilt.data.local.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mendelin.catpediahilt.data.local.entity.BreedDetailsEntity
import com.mendelin.catpediahilt.data.local.entity.BreedEntity

@Database(entities = [BreedEntity::class, BreedDetailsEntity::class], version = 1)
@TypeConverters(ConversterStringList::class)
abstract class CatsDatabase : RoomDatabase() {
    abstract fun catsDao(): CatsDao

    companion object {
        private var instance: CatsDatabase? = null

        fun getDatabase(context: Context): CatsDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, CatsDatabase::class.java, "CatsDB")
                    .build()
            }

            return instance!!
        }
    }
}