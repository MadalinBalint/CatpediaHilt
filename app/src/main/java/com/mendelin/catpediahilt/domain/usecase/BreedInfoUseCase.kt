package com.mendelin.catpediahilt.domain.usecase

import com.mendelin.catpediahilt.data.remote.CatpediaRepository
import com.mendelin.catpediahilt.domain.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BreedInfoUseCase @Inject constructor(private val repository: CatpediaRepository) {
    operator fun invoke(breedId: String, limit: Int = 8) = flow {
        try {
            emit(Resource.Loading())
            val breedInfo = repository.searchByBreed(breedId, limit)
            emit(Resource.Success(breedInfo))
        } catch (ex: Exception) {
            emit(Resource.Error(message = ex.localizedMessage ?: "Exception"))
        }
    }
}