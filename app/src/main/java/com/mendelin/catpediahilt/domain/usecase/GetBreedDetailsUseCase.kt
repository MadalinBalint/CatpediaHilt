package com.mendelin.catpediahilt.domain.usecase

import com.mendelin.catpediahilt.data.local.OfflineRepository
import com.mendelin.catpediahilt.domain.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBreedDetailsUseCase @Inject constructor(private val repository: OfflineRepository) {
    operator fun invoke(id: String) = flow {
        try {
            emit(Resource.Loading())
            val breedDetails = repository.getBreedDetails(id)
            emit(Resource.Success(breedDetails))
        } catch (ex: Exception) {
            emit(Resource.Error(message = ex.localizedMessage ?: "Exception"))
        }
    }
}