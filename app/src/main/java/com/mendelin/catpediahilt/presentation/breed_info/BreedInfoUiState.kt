package com.mendelin.catpediahilt.presentation.breed_info

import com.mendelin.catpediahilt.domain.model.BreedDetails

data class BreedInfoUiState(
    var breedInfo: BreedDetails? = null,
    var isLoading: Boolean = false,
    var isFailed: Pair<Boolean, String> = Pair(false, ""),
    var isOffline: Boolean = false,
)
