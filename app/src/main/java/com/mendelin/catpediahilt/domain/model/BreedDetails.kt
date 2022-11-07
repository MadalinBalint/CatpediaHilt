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
    val life_span: String = "",
    val imageUrls: List<String> = emptyList(),
)

fun BreedDetails.toEntity(): BreedDetailsEntity =
    BreedDetailsEntity(id, name, country, temperament, description, life_span, imageUrls)