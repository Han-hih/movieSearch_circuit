package com.app.movie_search_circuit.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.app.movie_search_circuit.data.repository.MovieResult
import com.app.movie_search_circuit.data.repository.MovieRepository
import com.slack.circuit.runtime.presenter.Presenter
import kotlinx.coroutines.launch

class MovieSearchPresenter(
    private val repository: MovieRepository,
) : Presenter<MovieSearchScreen.State> {

    @Composable
    override fun present(): MovieSearchScreen.State {
        var selectedTab by rememberSaveable { mutableStateOf(MovieSearchScreen.Tab.HOME) }
        var query by rememberSaveable { mutableStateOf("") }
        var isInitialLoading by remember { mutableStateOf(false) }
        var isSearching by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        var popularMovies by remember { mutableStateOf<List<MovieSearchScreen.MovieItem>>(emptyList()) }
        var latestMovies by remember { mutableStateOf<List<MovieSearchScreen.MovieItem>>(emptyList()) }
        var searchMovies by remember { mutableStateOf<List<MovieSearchScreen.MovieItem>>(emptyList()) }

        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            isInitialLoading = true
            errorMessage = null

            runCatching {
                repository.getPopularMovies() to repository.getNowPlayingMovies()
            }.onSuccess { (popular, latest) ->
                popularMovies = popular.map { it.toMovieItem() }
                latestMovies = latest.map { it.toMovieItem() }
            }.onFailure {
                errorMessage = "초기 영화 목록을 불러오지 못했어요."
            }

            isInitialLoading = false
        }

        val eventSink: (MovieSearchScreen.Event) -> Unit = { event ->
            when (event) {
                is MovieSearchScreen.Event.TabChanged -> {
                    selectedTab = event.tab
                }

                is MovieSearchScreen.Event.QueryChanged -> {
                    query = event.query
                }

                MovieSearchScreen.Event.SearchClicked -> {
                    if (query.isNotBlank()) {
                        scope.launch {
                            isSearching = true
                            errorMessage = null

                            runCatching {
                                repository.searchMovies(query.trim())
                            }.onSuccess { result ->
                                searchMovies = result.map { it.toMovieItem() }
                            }.onFailure {
                                errorMessage = "TMDB 검색에 실패했어요. API 키를 확인해 주세요."
                            }

                            isSearching = false
                        }
                    }
                }
            }
        }

        return MovieSearchScreen.State(
            selectedTab = selectedTab,
            query = query,
            isInitialLoading = isInitialLoading,
            isSearching = isSearching,
            errorMessage = errorMessage,
            popularMovies = popularMovies,
            latestMovies = latestMovies,
            searchMovies = searchMovies,
            eventSink = eventSink,
        )
    }
}

private fun MovieResult.toMovieItem(): MovieSearchScreen.MovieItem {
    return MovieSearchScreen.MovieItem(
        id = id,
        title = title,
        releaseDate = releaseDate,
    )
}
