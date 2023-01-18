package com.mendelin.catpediahilt.presentation.breeds_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mendelin.catpediahilt.data.local.entity.BreedEntity
import com.mendelin.catpediahilt.data.local.entity.toBreed
import com.mendelin.catpediahilt.data.remote.model.BreedsListModel
import com.mendelin.catpediahilt.data.remote.model.toBreed
import com.mendelin.catpediahilt.domain.Resource
import com.mendelin.catpediahilt.domain.model.Breed
import com.mendelin.catpediahilt.domain.usecase.BreedsListUseCase
import com.mendelin.catpediahilt.domain.usecase.CreateBreedUseCase
import com.mendelin.catpediahilt.domain.usecase.GetBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val listUseCase: BreedsListUseCase,
    private val createBreedUseCase: CreateBreedUseCase,
    private val getBreedsUseCase: GetBreedsUseCase,
) : ViewModel() {
    private val _viewState = MutableStateFlow(BreedsUiState())
    val viewState = _viewState.asStateFlow()

    /* Offline mode */
    fun fetchOfflineBreedsList(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            getBreedsUseCase().flowOn(dispatcher)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            _viewState.update { state ->
                                state.copy(
                                    isLoading = true,
                                    breeds = emptyList(),
                                    isFailed = Pair(false, ""),
                                    isOffline = true
                                )
                            }
                        }
                        is Resource.Success -> {
                            Log.d("TAG", "size = ${it.data?.size}")
                            _viewState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    breeds = it.data?.sortedBy { breed -> breed.name }
                                        ?.map(BreedEntity::toBreed)
                                        ?: emptyList(),
                                    isFailed = Pair(false, ""),
                                    isOffline = true
                                )
                            }
                        }
                        is Resource.Error -> {
                            Log.d("TAG", it.message ?: "error")

                            _viewState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    breeds = emptyList(),
                                    isFailed = Pair(true, it.message ?: "Unexpected error!"),
                                    isOffline = true
                                )
                            }
                        }
                    }
                }
        }
    }

   fun createOfflineBreedsList(breed: Breed, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
       viewModelScope.launch {
           createBreedUseCase(breed).flowOn(dispatcher)
               .collect {
                   when (it) {
                       is Resource.Loading -> {
                           Log.d("TAG", "insert ${breed.id} in progress")
                       }
                       is Resource.Success -> {
                           Log.d("TAG", "insert ${breed.id} success")
                       }
                       is Resource.Error -> {
                           Log.d("TAG", it.message ?: "insert ${breed.id} error")
                       }
                   }
               }
       }
   }

    /* Online mode */
    fun fetchBreedsList(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            listUseCase().flowOn(dispatcher)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            _viewState.update { state ->
                                state.copy(
                                    isLoading = true,
                                    breeds = emptyList(),
                                    isFailed = Pair(false, ""),
                                    isOffline = false
                                )
                            }
                        }
                        is Resource.Success -> {
                            _viewState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    breeds = it.data?.sortedBy { breed -> breed.name }
                                        ?.map(BreedsListModel::toBreed)
                                        ?: emptyList(),
                                    isFailed = Pair(false, ""),
                                    isOffline = false
                                )
                            }
                        }
                        is Resource.Error -> {
                            Log.d("TAG", it.message ?: "error")

                            _viewState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    breeds = emptyList(),
                                    isFailed = Pair(true, it.message ?: "Unexpected error!"),
                                    isOffline = false
                                )
                            }
                        }
                    }
                }
        }
    }
}