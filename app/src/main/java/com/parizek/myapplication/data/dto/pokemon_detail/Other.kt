package com.parizek.myapplication.data.dto.pokemon_detail

import com.squareup.moshi.Json

data class Other(
    @Json(name = "dream_world") val dreamWorld: DreamWorld?,
    @Json(name = "official-artwork") val officialArtwork: OfficialArtwork?,

    )