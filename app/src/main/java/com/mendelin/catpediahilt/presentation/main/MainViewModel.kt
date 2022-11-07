package com.mendelin.catpediahilt.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mendelin.catpediahilt.data.local.entity.BreedDetailsEntity
import com.mendelin.catpediahilt.data.local.entity.BreedEntity
import com.mendelin.catpediahilt.data.remote.model.BreedSearchModel
import com.mendelin.catpediahilt.data.remote.model.BreedsListModel
import com.mendelin.catpediahilt.domain.Resource
import com.mendelin.catpediahilt.domain.model.Breed
import com.mendelin.catpediahilt.domain.model.BreedDetails
import com.mendelin.catpediahilt.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val listUseCase: BreedsListUseCase,
    private val infoUseCase: BreedInfoUseCase,

    private val createBreedUseCase: CreateBreedUseCase,
    private val getBreedsUseCase: GetBreedsUseCase,
    private val createBreedDetailsUseCase: CreateBreedDetailsUseCase,
    private val getBreedDetailsUseCase: GetBreedDetailsUseCase,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isFailed = MutableStateFlow(Pair(false, ""))
    val isFailed = _isFailed.asStateFlow()

    private val _breedsList = MutableStateFlow<List<BreedsListModel>>(emptyList())
    val breedsList = _breedsList.asStateFlow()

    private val _breedInfo = MutableStateFlow<List<BreedSearchModel>>(emptyList())
    val breedInfo = _breedInfo.asStateFlow()

    private val _offlineBreedsList = MutableStateFlow<List<BreedEntity>>(emptyList())
    val offlineBreedsList = _offlineBreedsList.asStateFlow()

    private val _offlineBreedDetails = MutableStateFlow<BreedDetailsEntity?>(null)
    val offlineBreedDetails = _offlineBreedDetails.asStateFlow()

    /* Offline mode */
    fun fetchOfflineBreedsList(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            getBreedsUseCase.invoke()
                .flowOn(dispatcher)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                            _offlineBreedsList.value = emptyList()
                            _isFailed.value = Pair(false, "")
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            it.data?.let { list ->
                                _offlineBreedsList.value = list.sortedBy { breed -> breed.name }
                            }
                            _isFailed.value = Pair(false, "")
                        }
                        is Resource.Error -> {
                            Log.d("TAG", it.message ?: "error")

                            _isLoading.value = false
                            _breedsList.value = emptyList()
                            _isFailed.value = Pair(true, it.message ?: "Unexpected error!")
                        }
                    }
                }
        }
    }

    fun createOfflineBreedsList(breed: Breed, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            createBreedUseCase.invoke(breed)
                .flowOn(dispatcher)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                            _isFailed.value = Pair(false, "")
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            _isFailed.value = Pair(false, "")
                        }
                        is Resource.Error -> {
                            Log.d("TAG", it.message ?: "error")

                            _isLoading.value = false
                            _isFailed.value = Pair(true, it.message ?: "Unexpected error!")
                        }
                    }
                }
        }
    }

    fun fetchOfflineBreedInfo(breedId: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            getBreedDetailsUseCase.invoke(breedId)
                .flowOn(dispatcher)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                            _offlineBreedDetails.value = null
                            _isFailed.value = Pair(false, "")
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            it.data?.let { info ->
                                _offlineBreedDetails.value = info
                            }
                            _isFailed.value = Pair(false, "")
                        }
                        is Resource.Error -> {
                            _isLoading.value = false
                            _offlineBreedDetails.value = null
                            _isFailed.value = Pair(true, it.message ?: "Unexpected error!")
                        }
                    }
                }
        }
    }

    fun createOfflineBreedsInfo(breedDetails: BreedDetails, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            createBreedDetailsUseCase.invoke(breedDetails)
                .flowOn(dispatcher)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                            _isFailed.value = Pair(false, "")
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            _isFailed.value = Pair(false, "")
                        }
                        is Resource.Error -> {
                            Log.d("TAG", it.message ?: "error")

                            _isLoading.value = false
                            _isFailed.value = Pair(true, it.message ?: "Unexpected error!")
                        }
                    }
                }
        }
    }

    /* Online mode */
    fun fetchBreedsList(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            listUseCase.invoke()
                .flowOn(dispatcher)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                            _breedsList.value = emptyList()
                            _isFailed.value = Pair(false, "")
                            delay(2000)
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            it.data?.let { list ->
                                _breedsList.value = list.sortedBy { breed -> breed.name }
                            }
                            _isFailed.value = Pair(false, "")
                        }
                        is Resource.Error -> {
                            Log.d("TAG", it.message ?: "error")

                            _isLoading.value = false
                            _breedsList.value = emptyList()
                            _isFailed.value = Pair(true, it.message ?: "Unexpected error!")
                        }
                    }
                }
        }
    }

    fun fetchBreedInfo(breedId: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch {
            infoUseCase.invoke(breedId)
                .flowOn(dispatcher)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                            _breedInfo.value = emptyList()
                            _isFailed.value = Pair(false, "")
                            delay(2000)
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            it.data?.let { info ->
                                _breedInfo.value = info
                            }
                            _isFailed.value = Pair(false, "")
                        }
                        is Resource.Error -> {
                            _isLoading.value = false
                            _breedInfo.value = emptyList()
                            _isFailed.value = Pair(true, it.message ?: "Unexpected error!")
                        }
                    }
                }
        }
    }
}