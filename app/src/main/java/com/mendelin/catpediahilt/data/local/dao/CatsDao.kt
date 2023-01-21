package com.mendelin.catpediahilt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mendelin.catpediahilt.data.local.entity.BreedDetailsEntity
import com.mendelin.catpediahilt.data.local.entity.BreedEntity
import com.mendelin.catpediahilt.domain.model.Breed
import com.mendelin.catpediahilt.domain.model.BreedDetails

@Dao
interface CatsDao {
    /* Breeds */
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = BreedEntity::class)
    suspend fun insertBreed(breed: Breed)

    @Query("SELECT * from breeds_list")
    fun getBreedsList(): List<BreedEntity>

    /* Breed details */
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = BreedDetailsEntity::class)
    suspend fun insertBreedDetails(breedDetails: BreedDetails)

    @Query("SELECT * from breed_details WHERE id = :id")
    suspend fun getBreedDetails(id: String): BreedDetailsEntity?
}