package com.mendelin.catpediahilt.data.remote.model

import androidx.annotation.Keep
import com.mendelin.catpediahilt.domain.model.BreedDetails

@Keep
data class BreedSearchModel(
    val breeds: List<BreedModel>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
)

fun List<BreedSearchModel>.toBreedDetails(): BreedDetails {
    val first = firstOrNull()
    if (first != null && first.breeds.isNotEmpty()) {
        val images = map { it.url }

        return BreedDetails(
            first.breeds[0].id,
            first.breeds[0].name,
            first.breeds[0].origin,
            first.breeds[0].temperament,
            first.breeds[0].description,
            first.breeds[0].life_span,
            images
        )
    }

    return BreedDetails()
}