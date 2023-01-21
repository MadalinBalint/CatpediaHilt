package com.mendelin.catpediahilt.data.local.dao

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mendelin.catpediahilt.data.local.entity.BreedDetailsEntity
import com.mendelin.catpediahilt.data.local.entity.BreedEntity

@Database(
    entities = [BreedEntity::class, BreedDetailsEntity::class],
    version = 3,
    autoMigrations = [
        AutoMigration(
            from = 2,
            to = 3,
            spec = CatsDatabase.Companion.AutoMigration_2_3::class
        )

    ]
)
@TypeConverters(ConversterStringList::class)
abstract class CatsDatabase : RoomDatabase() {
    abstract fun catsDao(): CatsDao


    companion object {
        private var instance: CatsDatabase? = null

        /* v2: added wikipedia_url -> breed_details */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE breed_details ADD COLUMN wikipedia_url TEXT NOT NULL DEFAULT ''")
            }
        }

        /* v3: renamed life_span to lifeSpan & wikipedia_url to wikipediaUrl -> breed_details */
        @RenameColumn(
            tableName = "breed_details",
            fromColumnName = "life_span",
            toColumnName = "lifeSpan"
        )
        @RenameColumn(
            tableName = "breed_details",
            fromColumnName = "wikipedia_url",
            toColumnName = "wikipediaUrl"
        )
        class AutoMigration_2_3 : AutoMigrationSpec


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