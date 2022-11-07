package com.mendelin.catpediahilt.data.remote.model

import androidx.annotation.Keep

@Keep
data class WeightItem(
    val imperial: String,
    val metric: String,
)