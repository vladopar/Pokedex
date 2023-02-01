package com.parizek.myapplication.data.dto.pokemon_detail

import com.squareup.moshi.Json

data class OfficialArtwork (
    @Json(name = "front_default") val frontDefault: String?
)