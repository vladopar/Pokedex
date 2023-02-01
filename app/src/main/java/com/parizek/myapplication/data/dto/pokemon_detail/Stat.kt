package com.parizek.myapplication.data.dto.pokemon_detail

import com.squareup.moshi.Json

data class Stat(
    @Json(name = "base_stat") val baseStat: Int,
    val stat: StatX
)