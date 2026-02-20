package com.app.movie_search_circuit.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "ko-KR",
        @Query("page") page: Int = 1,
    ): TmdbSearchResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "ko-KR",
        @Query("page") page: Int = 1,
    ): TmdbSearchResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String = "ko-KR",
        @Query("include_adult") includeAdult: Boolean = false,
    ): TmdbSearchResponse
}
