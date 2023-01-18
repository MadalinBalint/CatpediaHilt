package com.mendelin.catpediahilt.domain.usecase

import com.mendelin.catpediahilt.data.local.OfflineRepository
import com.mendelin.catpediahilt.domain.Resource
import com.mendelin.catpediahilt.domain.model.Breed
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateBreedUseCase @Inject constructor(private val repository: OfflineRepository) {
   suspend operator fun invoke(breed: Breed) = flow {
        try {
            emit(Resource.Loading())
            repository.insertBreed(breed)
            emit(Resource.Success(null))
        } catch (ex: Exception) {
            emit(Resource.Error(message = ex.localizedMessage ?: "Exception"))
        }
    }
}