package com.mendelin.catpediahilt.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mendelin.catpediahilt.domain.model.Breed

@Keep
@Entity(tableName = "breeds_list")
data class BreedEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val country: String,
    val imageUrl: String,
)

fun BreedEntity.toBreed(): Breed =
    Breed(id, name, country, imageUrl)