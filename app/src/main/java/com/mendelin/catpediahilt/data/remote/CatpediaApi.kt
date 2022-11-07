package com.mendelin.catpediahilt.data.remote

import com.mendelin.catpediahilt.data.remote.model.BreedSearchModel
import com.mendelin.catpediahilt.data.remote.model.BreedsListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CatpediaApi {
    @GET("v1/breeds")
    suspend fun getBreedsList(): List<BreedsListModel>

    @GET("v1/images/search")
    suspend fun searchByBreed(
        @Query("breed_ids") breedId: String,
        @Query("limit") limit: Int = 8,
    ): List<BreedSearchModel>
}