package com.mendelin.catpediahilt.data.local.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mendelin.catpediahilt.data.local.entity.BreedDetailsEntity
import com.mendelin.catpediahilt.data.local.entity.BreedEntity

@Database(entities = [BreedEntity::class, BreedDetailsEntity::class], version = 2)
@TypeConverters(ConversterStringList::class)
abstract class CatsDatabase : RoomDatabase() {
    abstract fun catsDao(): CatsDao

    companion object {
        private var instance: CatsDatabase? = null

        /* v2: added wikipedia_url */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE breed_details ADD COLUMN wikipedia_url TEXT NOT NULL DEFAULT ''")
            }
        }

        val dbMigrations: Array<Migration> = arrayOf(MIGRATION_1_2)

        fun getDatabase(context: Context): CatsDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, CatsDatabase::class.java, "CatsDB")
                    .addMigrations(*dbMigrations)
                    .build()
            }

            return instance!!
        }
    }
}