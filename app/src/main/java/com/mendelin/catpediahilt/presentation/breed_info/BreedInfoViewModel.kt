package com.mendelin.catpediahilt.presentation.breed_info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mendelin.catpediahilt.data.local.entity.toDetails
import com.mendelin.catpediahilt.data.remote.model.toDetails
import com.mendelin.catpediahilt.domain.Resource
import com.mendelin.catpediahilt.domain.model.BreedDetails
import com.mendelin.catpediahilt.domain.usecase.BreedInfoUseCase
import com.mendelin.catpediahilt.domain.usecase.CreateBreedDetailsUseCase
import com.mendelin.catpediahilt.domain.usecase.GetBreedDetailsUseCase
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
class BreedInfoViewModel @Inject constructor(
    private val infoUseCase: BreedInfoUseCase,
    private val createBreedDetailsUseCase: CreateBreedDetailsUseCase,
    private val getBreedDetailsUseCase: GetBreedDetailsUseCase,
) : ViewModel() {
    private val _viewState = MutableStateFlow(BreedInfoUiState())
    val viewState = _viewState.asStateFlow()

    /* Offline mode */
    fun fetchOfflineBreedInfo(breedId: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            getBreedDetailsUseCase(breedId).flowOn(dispatcher)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            _viewState.update { state ->
                                state.copy(
                                    isLoading = true,
                                    breedInfo = null,
                                    isFailed = Pair(false, ""),
                                    isOffline = true
                                )
                            }
                        }
                        is Resource.Success -> {
                            _viewState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    breedInfo = it.data?.toDetails(),
                                    isFailed = Pair(false, ""),
                                    isOffline = true
                                )
                            }
                        }
                        is Resource.Error -> {
                            _viewState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    breedInfo = null,
                                    isFailed = Pair(true, it.message ?: "Unexpected error!"),
                                    isOffline = true
                                )
                            }
                        }
                    }
                }
        }
    }

    fun createOfflineBreedsInfo(breedDetails: BreedDetails, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            createBreedDetailsUseCase(breedDetails).flowOn(dispatcher)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            Log.d("TAG", "insert ${breedDetails.id} in progress")
                        }
                        is Resource.Success -> {
                            Log.d("TAG", "insert ${breedDetails.id} success")
                        }
                        is Resource.Error -> {
                            Log.d("TAG", it.message ?: "insert ${breedDetails.id} error")
                        }
                    }
                }
        }
    }

    /* Online mode */
    fun fetchBreedInfo(breedId: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            infoUseCase(breedId).flowOn(dispatcher)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            _viewState.update { state ->
                                state.copy(
                                    isLoading = true,
                                    breedInfo = null,
                                    isFailed = Pair(false, ""),
                                    isOffline = false
                                )
                            }
                        }
                        is Resource.Success -> {
                            _viewState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    breedInfo = it.data?.toDetails(),
                                    isFailed = Pair(false, ""),
                                    isOffline = false
                                )
                            }
                        }
                        is Resource.Error -> {
                            _viewState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    breedInfo = null,
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