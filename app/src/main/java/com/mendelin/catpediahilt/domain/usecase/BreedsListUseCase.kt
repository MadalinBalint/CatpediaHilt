package com.mendelin.catpediahilt.domain.usecase

import com.mendelin.catpediahilt.data.remote.CatpediaRepository
import com.mendelin.catpediahilt.domain.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BreedsListUseCase @Inject constructor(private val repository: CatpediaRepository) {
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