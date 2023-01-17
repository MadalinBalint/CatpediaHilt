package com.mendelin.catpediahilt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.mendelin.catpediahilt.data.local.entity.BreedDetailsEntity
import com.mendelin.catpediahilt.data.local.entity.BreedEntity

@Dao
interface CatsDao {
    /* Breeds */
    @Insert(onConflict = REPLACE)
    suspend fun insertBreed(breedEntity: BreedEntity)

    @Query("SELECT * from breeds_list")
    fun getBreedsList(): List<BreedEntity>

    /* Breed details */
    @Insert(onConflict = REPLACE)
    suspend fun insertBreedDetails(breedDetailsEntity: BreedDetailsEntity)

    @Query("SELECT * from breed_details WHERE id = :id")
    suspend fun getBreedDetails(id: String): BreedDetailsEntity?
}