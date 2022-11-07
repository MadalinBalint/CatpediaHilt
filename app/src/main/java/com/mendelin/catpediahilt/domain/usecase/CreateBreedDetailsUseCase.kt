package com.mendelin.catpediahilt.domain.usecase

import com.mendelin.catpediahilt.data.local.OfflineRepository
import com.mendelin.catpediahilt.domain.Resource
import com.mendelin.catpediahilt.domain.model.BreedDetails
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateBreedDetailsUseCase @Inject constructor(private val repository: OfflineRepository) {
    operator fun invoke(breedDetails: BreedDetails) = flow {
        try {
            emit(Resource.Loading())
            repository.insertBreedDetails(breedDetails)
            emit(Resource.Success(null))
        } catch (ex: Exception) {
            emit(Resource.Error(message = ex.localizedMessage ?: "Exception"))
        }
    }
}