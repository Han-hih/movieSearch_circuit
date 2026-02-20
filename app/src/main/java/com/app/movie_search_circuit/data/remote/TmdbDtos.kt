package com.app.movie_search_circuit.data.remote

import com.google.gson.annotations.SerializedName

data class TmdbSearchResponse(
    @SerializedName("results")
    val results: List<TmdbMovieDto> = emptyList(),
)

data class TmdbMovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String? = null,
)
