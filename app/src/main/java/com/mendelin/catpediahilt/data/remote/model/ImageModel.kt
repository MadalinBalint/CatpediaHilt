package com.mendelin.catpediahilt.data.remote.model

import androidx.annotation.Keep

@Keep
data class ImageModel(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String,
)