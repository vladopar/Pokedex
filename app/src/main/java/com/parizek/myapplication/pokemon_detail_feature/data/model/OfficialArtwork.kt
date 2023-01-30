package com.parizek.myapplication.pokemon_detail_feature.data.model

import com.squareup.moshi.Json

data class OfficialArtwork (
    @Json(name = "front_default") val frontDefault: String?
)