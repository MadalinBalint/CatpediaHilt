package com.mendelin.catpediahilt.data.remote

import com.mendelin.catpediahilt.data.remote.model.BreedSearchModel
import com.mendelin.catpediahilt.data.remote.model.BreedsListModel
import javax.inject.Inject

class CatpediaRepository @Inject constructor(private val api: CatpediaApi) {
    suspend fun getBreedsList(): List<BreedsListModel> =
        api.getBreedsList()

    suspend fun searchByBreed(breedId: String, limit: Int = 8): List<BreedSearchModel> =
        api.searchByBreed(breedId, limit)
}