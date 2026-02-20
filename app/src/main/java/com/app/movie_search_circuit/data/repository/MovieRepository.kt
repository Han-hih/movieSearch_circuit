package com.app.movie_search_circuit.data.repository

import com.app.movie_search_circuit.data.remote.TmdbApi
import com.app.movie_search_circuit.data.remote.TmdbMovieDto

class MovieRepository(
    private val tmdbApi: TmdbApi,
) {
    private val posterBaseUrl = "https://image.tmdb.org/t/p/w500"

    suspend fun getPopularMovies(): List<MovieResult> {
        return tmdbApi.getPopularMovies().results.map(::toMovieResult)
    }

    suspend fun getNowPlayingMovies(): List<MovieResult> {
        return tmdbApi.getNowPlayingMovies()
            .results
            .map(::toMovieResult)
            .sortedByDescending { it.releaseDate }
    }

    suspend fun searchMovies(query: String): List<MovieResult> {
        return tmdbApi.searchMovies(query = query).results.map(::toMovieResult)
    }

    private fun toMovieResult(dto: TmdbMovieDto): MovieResult {
        return MovieResult(
            id = dto.id,
            title = dto.title,
            releaseDate = dto.releaseDate.orEmpty(),
            posterUrl = dto.posterPath?.let { "$posterBaseUrl$it" }.orEmpty(),
        )
    }
}

data class MovieResult(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val posterUrl: String,
)
