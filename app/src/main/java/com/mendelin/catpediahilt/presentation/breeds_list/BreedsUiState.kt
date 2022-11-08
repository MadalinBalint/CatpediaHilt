package com.mendelin.catpediahilt.presentation.breeds_list

import com.mendelin.catpediahilt.domain.model.Breed

data class BreedsUiState(
    var breeds: List<Breed> = emptyList(),
    var isLoading: Boolean = false,
    var isFailed: Pair<Boolean, String> = Pair(false, ""),
    var isOffline: Boolean = false,
)
