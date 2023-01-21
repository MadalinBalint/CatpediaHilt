package com.mendelin.catpediahilt.domain.model

import androidx.annotation.Keep
import com.mendelin.catpediahilt.data.local.entity.BreedDetailsEntity

@Keep
data class BreedDetails(
    val id: String = "",
    val name: String = "",
    val country: String = "",
    val temperament: String = "",
    val description: String = "",
    val lifeSpan: String = "",
    val imageUrls: List<String> = emptyList(),
    val wikipediaUrl: String = "",
)