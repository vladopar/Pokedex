package com.parizek.myapplication.pokemon_detail_feature.data.dto

import com.squareup.moshi.Json

data class Stat(
    @Json(name = "base_stat") val baseStat: Int,
    val stat: StatX
)