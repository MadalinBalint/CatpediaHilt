package com.mendelin.catpediahilt.data.local

import android.util.Log
import com.mendelin.catpediahilt.data.local.dao.CatsDao
import com.mendelin.catpediahilt.data.local.entity.BreedDetailsEntity
import com.mendelin.catpediahilt.data.local.entity.BreedEntity
import com.mendelin.catpediahilt.domain.model.Breed
import com.mendelin.catpediahilt.domain.model.BreedDetails
import com.mendelin.catpediahilt.domain.model.toEntity
import javax.inject.Inject

class OfflineRepository @Inject constructor(private val catsDao: CatsDao) {
    suspend fun insertBreed(breed: Breed) =
        catsDao.insertBreed(breed.toEntity())

    fun getBreedsList(): List<BreedEntity> =
        catsDao.getBreedsList()

    suspend fun insertBreedDetails(breedDetails: BreedDetails) =
        catsDao.insertBreedDetails(breedDetails.toEntity())

    suspend fun getBreedDetails(id: String): BreedDetailsEntity? =
        catsDao.getBreedDetails(id)
}