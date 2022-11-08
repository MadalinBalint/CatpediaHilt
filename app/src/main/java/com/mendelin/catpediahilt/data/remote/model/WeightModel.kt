package com.mendelin.catpediahilt.data.remote.model

import androidx.annotation.Keep

@Keep
data class WeightModel(
    val imperial: String,
    val metric: String,
)