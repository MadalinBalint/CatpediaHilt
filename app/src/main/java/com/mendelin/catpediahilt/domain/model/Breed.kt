package com.mendelin.catpediahilt.domain.model

import androidx.annotation.Keep
import com.mendelin.catpediahilt.data.local.entity.BreedEntity

@Keep
data class Breed(
    val id: String,
    val name: String,
    val country: String,
    val imageUrl: String,
)

fun Breed.toEntity(): BreedEntity =
    BreedEntity(id, name, country, imageUrl)
