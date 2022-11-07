package com.mendelin.catpediahilt.domain.usecase

import com.mendelin.catpediahilt.data.local.OfflineRepository
import com.mendelin.catpediahilt.domain.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(private val repository: OfflineRepository) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val breedsList = repository.getBreedsList()
            emit(Resource.Success(breedsList))
        } catch (ex: Exception) {
            emit(Resource.Error(message = ex.localizedMessage ?: "Exception"))
        }
    }
}