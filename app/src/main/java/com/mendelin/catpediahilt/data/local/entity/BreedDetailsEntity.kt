package com.mendelin.catpediahilt.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mendelin.catpediahilt.data.local.dao.ConversterStringList
import com.mendelin.catpediahilt.domain.model.BreedDetails

@Keep
@Entity(tableName = "breed_details")
data class BreedDetailsEntity(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val country: String = "",
    val temperament: String = "",
    val description: String = "",
    val life_span: String = "",
    @TypeConverters(ConversterStringList::class)
    val imageUrls: List<String> = emptyList()
)

fun BreedDetailsEntity.toDetails(): BreedDetails =
    BreedDetails(id, name, country, temperament, description, life_span, imageUrls)
