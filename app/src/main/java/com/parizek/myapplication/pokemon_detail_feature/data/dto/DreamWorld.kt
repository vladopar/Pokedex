package com.parizek.myapplication.pokemon_detail_feature.data.dto

import com.squareup.moshi.Json

data class DreamWorld(
    @Json(name = "front_default") val frontDefault: String?,
)