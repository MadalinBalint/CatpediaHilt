package com.mendelin.catpediahilt.data.local

import com.mendelin.catpediahilt.data.local.dao.CatsDao
import com.mendelin.catpediahilt.data.local.entity.BreedDetailsEntity
import com.mendelin.catpediahilt.data.local.entity.BreedEntity
import com.mendelin.catpediahilt.domain.model.Breed
import com.mendelin.catpediahilt.domain.model.BreedDetails
import javax.inject.Inject

class OfflineRepository @Inject constructor(private val catsDao: CatsDao) {
    suspend fun insertBreed(breed: Breed) =
        catsDao.insertBreed(breed)

    fun getBreedsList(): List<BreedEntity> =
        catsDao.getBreedsList()

    suspend fun insertBreedDetails(breedDetails: BreedDetails) =
        catsDao.insertBreedDetails(breedDetails)

    suspend fun getBreedDetails(id: String): BreedDetailsEntity? =
        catsDao.getBreedDetails(id)
}